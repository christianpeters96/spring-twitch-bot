@echo off
set dir=%~dp0
set dir=%dir:\=/%serve.sh
set dir=%dir:F:=/cygdrive/f%

echo %dir%

C:\cygwin64\bin\bash -l %dir%
