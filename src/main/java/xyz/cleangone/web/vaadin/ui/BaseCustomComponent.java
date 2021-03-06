package xyz.cleangone.web.vaadin.ui;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.CustomComponent;

public class BaseCustomComponent extends CustomComponent
{
    protected AbstractOrderedLayout mainLayout;

    public BaseCustomComponent(String caption, AbstractOrderedLayout layout)
    {
        if (caption != null) setCaption(caption);

        mainLayout = layout;
        mainLayout.setMargin(false);
        mainLayout.setSpacing(true);
    }
}