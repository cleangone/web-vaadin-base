package xyz.cleangone.web.vaadin.ui;

import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.EditorSaveListener;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.renderers.DateRenderer;
import org.vaadin.dialogs.ConfirmDialog;
import xyz.cleangone.data.aws.dynamo.entity.base.BaseEntity;
import xyz.cleangone.data.aws.dynamo.entity.base.EntityField;
import xyz.cleangone.web.vaadin.util.CountingDataProvider;
import xyz.cleangone.web.vaadin.util.MultiFieldFilter;
import xyz.cleangone.web.vaadin.util.VaadinUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;

import static xyz.cleangone.web.vaadin.util.VaadinUtils.createDeleteButton;

public class EntityTreeGrid<T extends BaseEntity> extends TreeGrid<T>
{
    protected final int ICON_COL_WIDTH = 80;
    protected Label countLabel = new Label();

    protected Column<T, String> addColumn(EntityField entityField, ValueProvider<T, String> valueProvider)
    {
        return addColumn(entityField, valueProvider, 1);
    }

    protected Column<T, String> addColumn(EntityField entityField, ValueProvider<T, String> valueProvider, int expandRatio)
    {
        return addColumn(valueProvider)
            .setId(entityField.getName()).setCaption(entityField.getDisplayName()).setExpandRatio(expandRatio);
    }

    protected Column<T, String> addSortColumn(EntityField entityField, ValueProvider<T, String> valueProvider, Setter<T, String> setter)
    {
        Column<T, String> col = addColumn(entityField, valueProvider, setter);
        sort(col);

        return col;
    }


    protected Column<T, String> addColumn(EntityField entityField, ValueProvider<T, String> valueProvider, Setter<T, String> setter)
    {
        return addColumn(entityField, valueProvider)
            .setEditorComponent(new TextField(), setter);
    }

    protected Column<T, Boolean> addBooleanColumn(EntityField entityField, ValueProvider<T, Boolean> valueProvider)
    {
        return addColumn(valueProvider)
            .setId(entityField.getName())
            .setCaption(entityField.getDisplayName());
    }

    protected Column<T, Boolean> addBooleanColumn(EntityField entityField, ValueProvider<T, Boolean> valueProvider, Setter<T, Boolean> setter)
    {
        return addBooleanColumn(entityField, valueProvider)
            .setEditorComponent(new CheckBox(), setter);
    }

    protected Column<T, Date> addDateColumn(EntityField entityField, ValueProvider<T, Date> valueProvider, DateFormat dateFormat, SortDirection direction)
    {
        Column<T, Date> col = addDateColumn(entityField, valueProvider, dateFormat, 1);
        sort(col, direction);
        return col;
    }

    protected Column<T, Date> addDateColumn(EntityField entityField, ValueProvider<T, Date> valueProvider, DateFormat dateFormat)
    {
        return addDateColumn(entityField, valueProvider, dateFormat, 1);
    }

    protected Column<T, Date> addDateColumn(EntityField entityField, ValueProvider<T, Date> valueProvider, DateFormat dateFormat, int expandRatio)
    {
        return addColumn(valueProvider)
            .setId(entityField.getName()).setCaption(entityField.getDisplayName())
            .setRenderer(new DateRenderer(dateFormat))
            .setExpandRatio(expandRatio);
    }

    protected Column<T, BigDecimal> addBigDecimalColumn(EntityField entityField, ValueProvider<T, BigDecimal> valueProvider)
    {
        return addBigDecimalColumn(entityField, valueProvider, 1);
    }

    protected Column<T, BigDecimal> addBigDecimalColumn(EntityField entityField, ValueProvider<T, BigDecimal> valueProvider, int expandRatio)
    {
        return addColumn(valueProvider)
            .setId(entityField.getName()).setCaption(entityField.getDisplayName()).setExpandRatio(expandRatio);
    }

    protected Column<T, LinkButton> addLinkButtonColumn(EntityField entityField, ValueProvider<T, LinkButton> valueProvider)
    {
        return addLinkButtonColumn(entityField, valueProvider, 1);
    }

    protected Column<T, LinkButton> addLinkButtonColumn(EntityField entityField, ValueProvider<T, LinkButton> valueProvider, int expandRatio)
    {
        return addComponentColumn(valueProvider).setId(entityField.getName()).setExpandRatio(expandRatio);
    }

    protected Button buildDeleteButton(T entity, String name)
    {
        String entityName = entity.getClass().getSimpleName();
        return (buildDeleteButton(entity,
            "Delete " + entityName, "Confirm " + entityName + " Delete", "Delete " + entityName + " '" + name + "'?"));
     }

    protected Button buildDeleteButton(T entity, String buttonDescription, String confirmDialogCaption, String deleteMsg)
    {
        Button button = createDeleteButton(buttonDescription);
        addDeleteClickListener(entity, button, confirmDialogCaption, deleteMsg);

        return button;
    }

    protected Column<T, Button> addIconButtonColumn(ValueProvider<T, Button> componentProvider)
    {
        return addComponentColumn(componentProvider).setWidth(ICON_COL_WIDTH);
    }

    protected void addDeleteClickListener(T entity, Button button, String confirmDialogCaption, String deleteMsg)
    {
        button.addClickListener(e -> {
            ConfirmDialog.show(getUI(), confirmDialogCaption, deleteMsg, "Delete", "Cancel", new ConfirmDialog.Listener() {
                public void onClose(ConfirmDialog dialog) {
                    if (dialog.isConfirmed()) { delete(entity); }
                }
            });
        });
    }

    protected void delete(T entity) { }

    protected void setEditor(EditorSaveListener<T> listener)
    {
        getEditor().setEnabled(true);
        getEditor().setBuffered(true);
        getEditor().addSaveListener(listener);
    }

    protected void setColumnFiltering(CountingDataProvider<T> dataProvider)
    {
        setColumnFiltering(new MultiFieldFilter<T>(dataProvider), appendHeaderRow(), dataProvider);
    }

    protected void setColumnFiltering(MultiFieldFilter<T> filter, HeaderRow filterHeader, CountingDataProvider<T> dataProvider) { }

    protected TextField addFilterField(EntityField entityField, ValueProvider<T, String> valueProvider, MultiFieldFilter<T> filter, HeaderRow filterHeader)
    {
        filter.addField(entityField, valueProvider);
        return VaadinUtils.addFilterField(entityField, filter, filterHeader);
    }

    protected void appendCountFooterRow(EntityField field)
    {
        FooterRow footerRow = appendFooterRow();
        footerRow.getCell(field.getName()).setComponent(countLabel);
    }
}
