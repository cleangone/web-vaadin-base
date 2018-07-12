package xyz.cleangone.web.vaadin.disclosure;

import com.vaadin.ui.AbstractOrderedLayout;
import xyz.cleangone.web.vaadin.ui.BaseCustomComponent;

public abstract class BaseDisclosure extends BaseCustomComponent
{
    private final ControllableDisclosurePanel disclosurePanel;

    public BaseDisclosure(String caption, AbstractOrderedLayout layout)
    {
        super(caption, layout);

        // provide a hook into open/close
        disclosurePanel = new ControllableDisclosurePanel(mainLayout, this);
        setCompositionRoot(disclosurePanel);
    }

    public void setOpen(boolean open) { }

    public abstract void setDisclosureCaption();

    public void setDisclosureCaption(String disclosureCaption)
    {
        disclosurePanel.setCaption(disclosureCaption);
    }
}
