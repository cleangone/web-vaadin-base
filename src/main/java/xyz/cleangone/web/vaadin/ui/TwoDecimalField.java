package xyz.cleangone.web.vaadin.ui;

import com.vaadin.data.*;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.ui.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TwoDecimalField extends TextField
{
    Binder<TwoDecimalHolder> binder = new Binder<>();

    public TwoDecimalField(String caption)
    {
        super(caption);
        binder.forField(this)
            .withConverter(new TwoDecimalConverter())
            .bind(TwoDecimalHolder::getBd, TwoDecimalHolder::setBd);
    }

    public BigDecimal getBigDecimalValue()
    {
        TwoDecimalHolder holder = new TwoDecimalHolder();
        try
        {
            binder.writeBean(holder);
            return holder.getBd();
        }
        catch (ValidationException e)
        {
            return null;
        }
    }

    class TwoDecimalHolder
    {
        BigDecimal bd;
        BigDecimal getBd()
        {
            return bd;
        }
        void setBd(BigDecimal bd)
        {
            if (bd == null) { bd = new BigDecimal(0); }
            this.bd = bd.setScale(2, RoundingMode.CEILING);
        }
    }

    class TwoDecimalConverter extends StringToBigDecimalConverter
    {
        TwoDecimalConverter()
        {
            super("Invalid format");
        } // error message

        @Override
        public Result<BigDecimal> convertToModel(String value, ValueContext context)
        {
            return super.convertToModel(value, context);
        }
    }
}
