package ru.ancndz.digitalcourse.views;


import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import ru.ancndz.digitalcourse.components.appnav.AppNav;
import ru.ancndz.digitalcourse.components.appnav.AppNavItem;
import ru.ancndz.digitalcourse.views.chat.ChatView;
import ru.ancndz.digitalcourse.views.labs.LabsView;
import ru.ancndz.digitalcourse.views.lectures.LecturesView;
import ru.ancndz.digitalcourse.views.main.MainView;
import ru.ancndz.digitalcourse.views.tests.TestsView;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Цифровой курс");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

        nav.addItem(new AppNavItem(MainView.PAGE_TITLE, MainView.class, "la la-th-list"));
        nav.addItem(new AppNavItem(LecturesView.PAGE_TITLE, LecturesView.class, "la la-list"));
        nav.addItem(new AppNavItem(LabsView.PAGE_TITLE, LabsView.class, "la la-th-list"));
        nav.addItem(new AppNavItem(TestsView.PAGE_TITLE, TestsView.class, "la la-th-list"));
        nav.addItem(new AppNavItem(ChatView.PAGE_TITLE, ChatView.class, "la la-comments"));

        return nav;
    }

    private Footer createFooter() {
        return new Footer();
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
