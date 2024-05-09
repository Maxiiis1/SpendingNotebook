package restControllers;

import contracts.categories.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoriesController {
    private final ICategoryService categoryService;
}
