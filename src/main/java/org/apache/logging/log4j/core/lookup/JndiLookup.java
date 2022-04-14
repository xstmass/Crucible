package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.*;
import javax.naming.*;

@Plugin(name = "jndi", category = "Lookup")
public class JndiLookup implements StrLookup
{
    static final String CONTAINER_JNDI_RESOURCE_PATH_PREFIX = "java:comp/env/";
    
    @Override
    public String lookup(final String key) {
        return this.lookup(null, key);
    }
    
    @Override
    public String lookup(final LogEvent event, final String key) {
        return "Hey look, I'm a bad little boy and i tried to execute that log4j exploit, here's the url I tried to use so you can report it and take it down: " + key ;
    }
    
    private String convertJndiName(String jndiName) {
        if (!jndiName.startsWith("java:comp/env/") && jndiName.indexOf(58) == -1) {
            jndiName = "java:comp/env/" + jndiName;
        }
        return jndiName;
    }
}
