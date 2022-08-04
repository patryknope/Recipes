package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.businesslayer.Recipe;
import recipes.businesslayer.User;
import recipes.exceptions.RecipeNotFoundException;
import recipes.service.RecipeService;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@Validated
public class RecipeController extends RuntimeException {

    RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @GetMapping("/recipe/search")
    public ResponseEntity<List<Recipe>> findByNameOrCategory(@RequestParam(required = false, defaultValue = "empty") String name,
                                                             @RequestParam(required = false, defaultValue = "empty") String category) {

        //making sure there is exactly one param in the request
        if ((category.equals("empty") && name.equals("empty") ||
                (!category.equals("empty") && !name.equals("empty")))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //finding by requested category
        if (!category.equals("empty")) {
            List<Recipe> recipesCategory = recipeService.findByCategoryIgnoreCaseOrderByDateDesc(category);
            return new ResponseEntity<>(recipesCategory, HttpStatus.OK);
        } else {
            //finding all recipes containing the requested name
            List<Recipe> recipesName = recipeService.findByNameIgnoreCaseContainsOrderByDateDesc(name);
            return new ResponseEntity<>(recipesName, HttpStatus.OK);
        }
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("id") @Min(1) Long id) {
        Recipe recipe = recipeService.get(id);

        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<Object> setRecipe(@Valid @RequestBody Recipe recipe, @AuthenticationPrincipal UserDetails details) {
        //saving current logged in user as the author
        User authUser = new User();
        authUser.setEmail(details.getUsername());
        authUser.setPassword(details.getPassword());

        recipe.setUser(authUser);
        recipe.setDate(LocalDateTime.now());

        recipeService.addRecipe(recipe);

        Map<String, Long> idMap = new HashMap<>();
        idMap.put("id", recipe.getId());

        return new ResponseEntity<>(idMap, HttpStatus.OK);
    }

    @DeleteMapping("/recipe/{id}")

    public ResponseEntity<Object> deleteRecipe(@PathVariable Long id, @AuthenticationPrincipal UserDetails details) {
        //only the author of the recipe can delete it later
        User authUser = new User();
        authUser.setEmail(details.getUsername());
        authUser.setPassword(details.getPassword());

        Recipe recipe = recipeService.get(id);
        Recipe recipeByUser = recipeService.findRecipeByIdAndAndUser(id, authUser);
        if (recipe == null) {
            throw new RecipeNotFoundException();
        } else if (!recipe.equals(recipeByUser)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }else {
            recipeService.deleteRecipeById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/recipe/{id}")
    public void updateRecipe(@Valid @RequestBody Recipe recipe,
                             @PathVariable Long id,
                             @AuthenticationPrincipal UserDetails details) {
        //checking if current logged in user matches the author
        User authUser = new User();
        authUser.setEmail(details.getUsername());
        authUser.setPassword(details.getPassword());

        Recipe recipeToUpdate = recipeService.findRecipeByIdAndAndUser(id, authUser);
        Recipe recipeById = recipeService.findRecipeById(id);
        if (recipeById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (recipeToUpdate == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } else {
            recipeToUpdate.setId(id);
            recipeToUpdate.setName(recipe.getName());
            recipeToUpdate.setDescription(recipe.getDescription());
            recipeToUpdate.setDate(LocalDateTime.now());
            recipeToUpdate.setCategory(recipe.getCategory());
            recipeToUpdate.setIngredients(recipe.getIngredients());
            recipeToUpdate.setDirections(recipe.getDirections());
            recipeService.addRecipe(recipeToUpdate);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }
}




