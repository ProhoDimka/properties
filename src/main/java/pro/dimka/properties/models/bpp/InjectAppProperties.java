package pro.dimka.properties.models.bpp;

import pro.dimka.properties.models.ConfigName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectAppProperties {
    ConfigName[] configs();
}
