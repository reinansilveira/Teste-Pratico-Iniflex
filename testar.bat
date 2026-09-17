@echo off
setlocal

cd /d "%~dp0"

where mvn >nul 2>&1
if not errorlevel 1 (
    call mvn --quiet clean verify
    if errorlevel 1 goto :erro
    java -jar target\teste-pratico-iniflex-1.0.0.jar
    if errorlevel 1 goto :erro
    goto :sucesso
)

echo Maven nao encontrado; executando somente o programa com o JDK.
if not exist target\classes mkdir target\classes

if exist target\fontes-principais.txt del /q target\fontes-principais.txt

for /r src\main\java %%f in (*.java) do echo "%%f">>target\fontes-principais.txt

javac -encoding UTF-8 -d target\classes @target\fontes-principais.txt
if errorlevel 1 goto :erro

java -cp target\classes br.com.iniflex.Principal
if errorlevel 1 goto :erro

:sucesso
echo.
echo Execucao concluida com sucesso.
pause
exit /b 0

:erro
echo.
echo Nao foi possivel executar. Confirme se o JDK 17 ou superior esta instalado.
pause
exit /b 1
