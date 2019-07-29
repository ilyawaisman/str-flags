# str-flags
[ ![Download](https://api.bintray.com/packages/ilyawaisman/maven/str-flags/images/download.svg?version=0.0.2) ](https://bintray.com/ilyawaisman/maven/str-flags/0.0.2/link)

String representation for flags collections. Library generates conversions between arbitrary flag collection represented by an interface and strings.

## Example

Given an interface for Unix access rights.

```java
enum Permission { Read, Write, Xecute }

@StrFlags
interface Rights {
    boolean isDirectory();
    Set<Permission> ownerPermissions();
    Set<Permission> groupPermissions();
    Set<Permission> everyonePermissions();
}
```

Two methods will be generated

```java
public class RightsConvert {
    public Rights fromString(String str); 
    public String toString(Rights obj); 
}
```

preforming converts of `Rights` objects from and to string representation like

```
dr--r-----
-rw-rw-r--
-rwxr-xr-x
```

Here for method `isDirectory` one flag `d` is generated, and for other methods groups of three flags `rwx` are generated.
`d` is taken from method's name, `rwx` from enum elements names.

## Rules

To use generator you need to provide an interface and annotate it with `@StrFlags`.
All interface methods must fall into one of the three categories:
* returning `boolean`,
* returning an enum,
* returning `Set` of an enum.

All methods must have zero params.

For a method returning `boolean` one flag is generated based on the first letter of its name where prefixes `is`/`are` are omitted.
For a method returning a `Set` of enum a group of flags is created, one for each enum element.
First letters of enum element names are used.
For a method returning single enum element meta-flag is generated.
It can take any value of enum elemenets first letters or "-" for `null`.
All letters used are lowercased.

Helper type `<interface_name>Convert` will be generated with two methods - `fromString` and `toString`.

## Gradle configuration

To use in Java project add dependency (in Groovy DSL):

```groovy
dependencies {
    implementation 'xyz.prpht.setflags:str-flags:0.0.2'
    annotationProcessor 'xyz.prpht.setflags:str-flags:0.0.2'
}
```

If you use modern Kotlin DSL for Gradle, add brackets:    

```kotlin
dependencies {
    implementation("xyz.prpht.setflags:str-flags:0.0.2")
    annotationProcessor("xyz.prpht.setflags:str-flags:0.0.2")
}
```

For Kotlin project use `kapt` instead of `annotationProcessor`.

## TODO

1. Add interfaces inheritance support.
2. ...

Feel free to add issues with bugs found and feature requests.
