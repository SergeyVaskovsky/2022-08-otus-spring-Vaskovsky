Домашнее задание
Обернуть приложение в docker-контейнер

Цель: деплоить приложение в современном DevOps-стеке
Результат: обёртка приложения в Docker

Описание/Пошаговая инструкция выполнения домашнего задания:
Внимание! Задание выполняется на основе любого сделанного Web-приложения

Обернуть приложение в docker-контейнер. Dockerfile принято располагать в корне репозитория. В image должна попадать JAR-приложения. Сборка в контейнере рекомендуется, но не обязательна.
БД в собственный контейнер оборачивать не нужно (если только Вы не используете кастомные плагины)
Настроить связь между контейнерами, с помощью docker-compose
Опционально: сделать это в локальном кубе.
Приложение желательно реализовать с помощью всех Best Practices Docker (логирование в stdout и т.д.)
Данное задание НЕ засчитывает предыдущие!


Версия с docker-compose запускается по адресу: http://localhost:8080

Версия задания с kubernetes запускается вот так: kubectl apply -f deployment.yaml
Приложение поднимется по адресу http://localhost:30001