package ru.ancndz.digitalcourse.views.main;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
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

@PageTitle(MainView.PAGE_TITLE)
@Route(value = "index", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class MainView extends Main implements HasComponents, HasStyle {

    public static final String PAGE_TITLE = "Главная";
    private OrderedList imageContainer;

    public MainView() {
        constructUI();
        final List<MainViewCard> mainViewCardList = loadLinks();
        mainViewCardList.forEach(imageContainer::add);
    }

    private List<MainViewCard> loadLinks() {
        final var linkList = new ArrayList<MainViewCard>();

        try (var file = Files.newInputStream(Path.of("data", "sites", "sites.xlsx"))) {
            final Workbook workbook = new XSSFWorkbook(file);
            final Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                final MainViewCard card = new MainViewCard(getCellStringValue(sheet, i, 0),
                                getCellStringValue(sheet, i, 1),
                                getCellStringValue(sheet, i, 2),
                                getCellStringValue(sheet, i, 3),
                                getCellStringValue(sheet, i, 4),
                                getCellStringValue(sheet, i, 5));

                card.addClickListener(event -> getUI()
                        .ifPresent(ui -> ui.getPage().open(card.getPageUrl(), "_blank")));
                linkList.add(card);
            }
        } catch (Exception ignored) {
        }
        return linkList;
    }

    private String getCellStringValue(final Sheet sheet, int row, int cell) {
        Cell result = sheet.getRow(row).getCell(cell);
        if (result == null) {
            return null;
        } else {
            return result.getStringCellValue();
        }
    }

    private void constructUI() {
        addClassNames("main-view", MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE,
                Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout mainContainer = new VerticalLayout();

        H2 mainHeader = new H2("Содержание дисциплины");
        mainHeader.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        mainContainer.add(mainHeader);


        addChapter(mainContainer, "Тема 1. Введение в паттерны",
                "Понятие паттерна проектирования\n" +
                "Описание паттернов проектирования");

        addChapter(mainContainer, "Тема 2. Основные идеи паттернов",
                "Классификация паттернов проектирования");

        addChapter(mainContainer, "Тема 3. Порождающие паттерны",
                "Паттерн Абстрактная фабрика\n" +
                        "Паттерн Строитель\n" +
                        "Паттерн Фабричный метод\n" +
                        "Паттерн Отложенная инициализация\n" +
                        "Паттерн Мультитон\n" +
                        "Паттерн Объектный пул\n" +
                        "Паттерн Получение ресурса есть инициализация\n" +
                        "Паттерн Прототип\n" +
                        "Паттерн Одиночка");
        addChapter(mainContainer, "Тема 4. Структурные паттерны",
                "Паттерн Адаптер\n" +
                        "Паттерн Мост\n" +
                        "Паттерн Компоновщик\n" +
                        "Паттерн Декоратор\n" +
                        "Паттерн Фасад\n" +
                        "Паттерн Единая точка входа\n" +
                        "Паттерн Приспособленец\n" +
                        "Паттерн Заместитель");
        addChapter(mainContainer, "Тема 5. Паттерны поведения",
                "Паттерн Цепочка обязанностей\n" +
                        "Паттерн Команда\n" +
                        "Паттерн Интерпретатор\n" +
                        "Паттерн Итератор\n" +
                        "Паттерн Посредник\n" +
                        "Паттерн Наблюдатель");
        addChapter(mainContainer, "Тема 6. Системные шаблоны",
                " ");

        VerticalLayout linksContainer = new VerticalLayout();
        H2 header = new H2("Литература, источники");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Ссылки для ознакомления");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        linksContainer.add(header, description);

        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(linksContainer);
        add(mainContainer, container, imageContainer);
    }

    private void addChapter(VerticalLayout mainContainer, String chapterHeaderText, String chapterTextText) {
        H3 chapterHeader = new H3(chapterHeaderText);
        chapterHeader.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.LARGE);

        Paragraph chapterText = new Paragraph(chapterTextText);
        chapterText.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.BODY);

        mainContainer.add(chapterHeader, chapterText);
    }
}
