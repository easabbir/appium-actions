# appium-actions
This project demonstrates incorporating ci/cd with appium


appium &>/dev/null &

we can run the android tests in the github runner itself and it can save a lot of money

1. Setup project
2. setup java
3. setup node
4. download appium
5. run appium
6. start emulator
7. run the project 


Generating report

    allure generate ./allure-results --clean
Display report using server

    allure open ./allure-report/ 