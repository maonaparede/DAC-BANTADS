
package com.tads.dac.saga.util;

public interface TemplateEmailInterface {
    
    public abstract String getSubject();
    public abstract String getTo();
    public abstract String getContent();
}
