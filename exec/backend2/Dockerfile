FROM python:3.8.5

ENV PYTHONUNBUFFERED 1

RUN apt-get -y update
RUN apt-get -y install vim

WORKDIR /web
COPY . .

RUN pip install --upgrade pip
RUN pip install -r requirements.txt

EXPOSE 8000
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]
