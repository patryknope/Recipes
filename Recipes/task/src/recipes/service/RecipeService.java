package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.businesslayer.Recipe;
import recipes.businesslayer.User;
import recipes.exceptions.RecipeNotFoundException;
import recipes.persistence.RecipeRepository;
import java.util.List;



@Service
public class RecipeService {

    RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    public Recipe findRecipeByIdAndAndUser(Long id, User user) {
        return recipeRepository.findRecipeByIdAndUser(id, user);
    }

    public void addRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public Recipe get(long id) {
        return recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);
    }
    public Recipe findRecipeById(Long id){
        return recipeRepository.findRecipeById(id);
    }

    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category){
        List<Recipe> recipes = recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
        return recipes;
    }
    public List<Recipe> findByNameIgnoreCaseContainsOrderByDateDesc(String name){
        List<Recipe> recipes = recipeRepository.findByNameIgnoreCaseContainsOrderByDateDesc(name);
        return recipes;
    }


}
