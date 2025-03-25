package ru.fink;

import contracts.categories.ICategoryService;
import contracts.transactions.ITransactionService;
import models.categories.Category;
import models.transaction.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"businessLogic", "restControllers", "dataAccess", "exceptionHandlers"})
@EnableJpaRepositories(basePackages = "dataAccess")
@EntityScan(basePackages = "models")
@SpringBootApplication
public class NotebookApplication implements CommandLineRunner{
    private final ITransactionService service;
    private final ICategoryService categoryService;

    public NotebookApplication(ITransactionService serv, ICategoryService categoryService1){
        service = serv;
        categoryService = categoryService1;
    }
    public static void main(String[] args) {
        SpringApplication.run(NotebookApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Category category = Category.builder().name("Рестораны").build();
        //categoryService.addCategory(category);
        //service.addTransaction(Transaction.builder().name("Оплата").category(category).build());
    }
}
