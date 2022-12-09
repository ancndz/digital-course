package ru.ancndz.digitalcourse.views.main;

import com.vaadin.flow.component.html.*;

import javax.annotation.Nullable;

public class MainViewCard extends ListItem {

    private String pageUrl;
    private String pageTitle;
    private String imageUrl;
    private String subTitle;
    private String pageDescription;
    private String bottomTag;

    public MainViewCard(String pageUrl,
            String pageTitle,
            String imageUrl,
            @Nullable String subTitle,
            @Nullable String pageDescription,
            String bottomTag) {
        this.pageUrl = pageUrl;
        this.pageTitle = pageTitle;
        this.imageUrl = imageUrl;
        this.subTitle = subTitle;
        this.pageDescription = pageDescription;
        this.bottomTag = bottomTag;

        construct();
    }

    private void construct() {
        this.addClassNames("bg-contrast-5", "flex", "flex-col", "items-start", "p-m", "rounded-l");
        this.getStyle().set("cursor", "pointer");

        final Div div = new Div();
        div.addClassNames("bg-contrast", "flex items-center", "justify-center", "mb-m", "overflow-hidden",
                "rounded-m w-full");
        div.setHeight("160px");

        if (imageUrl != null) {
            final Image image = new Image();
            image.setWidth("100%");
            image.setSrc(imageUrl);
            image.setAlt(pageTitle);
            div.add(image);
        }

        final Span header = new Span();
        header.addClassNames("text-xl", "font-semibold");
        header.setText(pageTitle);

        this.add(div, header);

        if (subTitle != null) {
            final Span subtitle = new Span();
            subtitle.addClassNames("text-s", "text-secondary");
            subtitle.setText(subTitle);
            add(subtitle);
        }

        if (pageDescription != null) {
            final Paragraph paragraph = new Paragraph(pageDescription);
            paragraph.addClassName("my-m");
            add(paragraph);
        }

        if (bottomTag != null) {
            final Span badge = new Span();
            badge.getElement().setAttribute("theme", "badge");
            badge.setText(bottomTag);
            this.add(badge);
        }

    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getPageDescription() {
        return pageDescription;
    }

    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

    public String getBottomTag() {
        return bottomTag;
    }

    public void setBottomTag(String bottomTag) {
        this.bottomTag = bottomTag;
    }
}
