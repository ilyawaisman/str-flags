<#-- @ftlvariable name="" type="xyz.prpht.strflags.generator.ModelType" -->

package ${pack};

import javax.annotation.Generated;
import java.util.Set;

import static xyz.prpht.strflags.meta.StrFlagsHelper.*;

@Generated("str-flags")
@SuppressWarnings({"SameParameterValue"})
public final class ${name}Convert {

    public static ${name} fromString(String str) {
        if (str.length() != ${length}) {
            throw fromStringException(str, "length must be ${length}");
        }

        return new __${name}Impl(
<#list members as member>
                ${member.fromStringExpr("str")}<#sep>,</#sep>
</#list>
        );
    }

    public static String toString(${name} obj) {
        return ""
<#list members as member>
                + ${member.toStringExpr("obj")}
</#list>
                ;
    }

    private static class __${name}Impl implements ${name} {

<#list members as member>
        private final ${member.type} ${member.name};
</#list>

        __${name}Impl(
<#list members as member>
                ${member.type} ${member.name}<#sep>,</#sep>
</#list>
        ) {
<#list members as member>
            this.${member.name} = ${member.name};
</#list>
        }
<#list members as member>

        @Override
        public ${member.type} ${member.name}() {
            return ${member.name};
        }
</#list>

        @Override
        public String toString() {
            return String.format("${name}[%f]", ${name}Convert.toString(this));
        }
    }
}
