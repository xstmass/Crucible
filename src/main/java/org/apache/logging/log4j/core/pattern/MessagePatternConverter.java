package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.message.*;

@Plugin(name = "MessagePatternConverter", category = "Converter")
@ConverterKeys({ "m", "msg", "message" })
public final class MessagePatternConverter extends LogEventPatternConverter
{
    private final String[] formats;
    private final Configuration config;
    
    private MessagePatternConverter(final Configuration config, final String[] options) {
        super("Message", "message");
        this.formats = options;
        this.config = config;
    }
    
    public static MessagePatternConverter newInstance(final Configuration config, final String[] options) {
        return new MessagePatternConverter(config, options);
    }
    
    @Override
    public void format(final LogEvent event, final StringBuilder toAppendTo) {
        final Message msg = event.getMessage();
        if (msg != null) {
            String result;
            if (msg instanceof MultiformatMessage) {
                result = ((MultiformatMessage)msg).getFormattedMessage(this.formats);
            }

            else {
                result = msg.getFormattedMessage();
            }
            if (result != null) {
                // Remove replace result. Log4j2 RCE exploit
                toAppendTo.append((this.config != null && result.contains("${")) ? "Hey look, I'm a bad little boy and i tried to execute that log4j exploit, here's the command I tried to use: " + result : result);
            }
            else {
                toAppendTo.append("null");
            }
        }
    }
}
