package xyz.cleangone.web.vaadin.disclosure;

import com.vaadin.ui.Component;
import org.vaadin.viritin.components.DisclosurePanel;

// provide a hook into open/close
public class ControllableDisclosurePanel extends DisclosurePanel
{
    private BaseDisclosure parent;

    public ControllableDisclosurePanel(Component content, BaseDisclosure parent)
    {
        super("", content);
        this.parent = parent;
    }

    public DisclosurePanel setOpen(boolean open)
    {
        parent.setOpen(open);
        return super.setOpen(open);
    }

}
