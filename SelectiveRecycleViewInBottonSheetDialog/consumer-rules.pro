# This library does not use reflection, runtime code generation, or serialization frameworks.
# No consumer ProGuard/R8 rules are required.
#
# If you encounter issues with aggressive shrinking in your app, you can temporarily keep the
# library package while diagnosing (not recommended for production):
#
# -keep class com.sagar.selectiverecycleviewinbottonsheetdialog.** { *; }
