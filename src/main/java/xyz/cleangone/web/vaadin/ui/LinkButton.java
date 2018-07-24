package xyz.cleangone.web.vaadin.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class LinkButton extends Button
{
    private String name;

    public LinkButton(String name, ClickListener listener)
    {
        super(name);
        this.name = name;
        addStyleName(ValoTheme.BUTTON_LINK);

        addClickListener(listener);
    }

    public String getName()
        {
            return name;
        }
}
