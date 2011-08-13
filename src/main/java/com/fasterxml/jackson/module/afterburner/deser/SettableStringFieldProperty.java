package com.fasterxml.jackson.module.afterburner.deser;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.deser.SettableBeanProperty;

public final class SettableStringFieldProperty
    extends OptimizedSettableBeanProperty<SettableStringFieldProperty>
{
    public SettableStringFieldProperty(SettableBeanProperty src,
            BeanPropertyMutator mutator, int index)
    {
        super(src, mutator, index);
    }

    public SettableStringFieldProperty(SettableStringFieldProperty src, JsonDeserializer<Object> deser) {
        super(src, deser);
    }

    @Override
    public SettableStringFieldProperty withValueDeserializer(JsonDeserializer<Object> deser) {
        return new SettableStringFieldProperty(this, deser);
    }
    
    @Override
    public SettableStringFieldProperty withMutator(BeanPropertyMutator mut) {
        return new SettableStringFieldProperty(_originalSettable, mut, _propertyIndex);
    }
    
    @Override
    public void deserializeAndSet(JsonParser jp, DeserializationContext ctxt,
            Object bean) throws IOException, JsonProcessingException
    {
        String value;
        JsonToken curr = jp.getCurrentToken();
        if (curr == JsonToken.VALUE_STRING) {
            value = jp.getText();
        } else {
            value = _convertToString(jp, ctxt, curr);
        }
        _propertyMutator.stringField(bean, _propertyIndex, value);
    }

    @Override
    public void set(Object bean, Object value) throws IOException {
        _propertyMutator.stringField(bean, _propertyIndex, (String) value);
    }
}
