@echo off
set dir=%~dp0
set dir=%dir:\=/%build.sh --win
set dir=%dir:F:=/cygdrive/f%

echo %dir%

C:\cygwin64\bin\bash -l %dir%
