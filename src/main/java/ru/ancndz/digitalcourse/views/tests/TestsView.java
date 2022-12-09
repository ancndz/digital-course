package ru.ancndz.digitalcourse.views.tests;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.ancndz.digitalcourse.views.MainLayout;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@PageTitle(TestsView.PAGE_TITLE)
@Route(value = "tests", layout = MainLayout.class)
public class TestsView extends Div implements AfterNavigationObserver {

    public static final String PAGE_TITLE = "Тестирование";

    Grid<Question> grid = new Grid<>();

    public TestsView() {
        addClassName("tests-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(this::createCard);
        add(grid);
    }

    private HorizontalLayout createCard(Question question) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(question.getQuestion());
        name.addClassName("name");
        header.add(name);

        ListBox<String> listBox = new ListBox<>();
        listBox.setItems(question.getAnswers().keySet());

        description.add(header, listBox);
        card.add(description);
        return card;
    }


    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        final List<Question> questions = new ArrayList<>();

        try (var file = Files.newInputStream(Path.of("data", "tests", "tests.xlsx"))) {
            final Workbook workbook = new XSSFWorkbook(file);
            final Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                final Question question = new Question(getCellStringValue(sheet, i, 0));

                question.addAnswer(getCellStringValue(sheet, i, 1), getIsRightAnswer(sheet, i, 1));
                question.addAnswer(getCellStringValue(sheet, i, 2), getIsRightAnswer(sheet, i, 2));
                question.addAnswer(getCellStringValue(sheet, i, 3), getIsRightAnswer(sheet, i, 3));
                question.addAnswer(getCellStringValue(sheet, i, 4), getIsRightAnswer(sheet, i, 4));

                questions.add(question);
            }
        } catch (Exception ignored) {
        }

        grid.setItems(questions);
    }

    private Boolean getIsRightAnswer(Sheet sheet, int i, int answerCellNumber) {
        return Objects.equals(getCellStringValue(sheet, i, 5), getCellStringValue(sheet, 0, answerCellNumber));
    }


    private String getCellStringValue(final Sheet sheet, int row, int cell) {
        Cell result = sheet.getRow(row).getCell(cell);
        if (result == null) {
            return null;
        } else {
            return result.getStringCellValue();
        }
    }
}
