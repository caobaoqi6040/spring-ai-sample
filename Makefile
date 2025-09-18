IMAGE_NAME ?= caobaoqi6040/spring-ai-sample
TAG ?= latest

.PHONY: build
build:
	docker build -f Dockerfile -t $(IMAGE_NAME):$(TAG) .

.PHONY: tag
tag:
	docker tag $(IMAGE_NAME):latest $(IMAGE_NAME):$(TAG)

.PHONY: tag-dev
tag-dev:
	docker tag $(IMAGE_NAME):latest $(IMAGE_NAME):dev

.PHONY: tag-release
tag-release:
	docker tag $(IMAGE_NAME):latest $(IMAGE_NAME):release

.PHONY: push
push:
	docker push $(IMAGE_NAME):$(TAG)

.PHONY: push-dev
push-dev:
	docker push $(IMAGE_NAME):dev

.PHONY: push-release
push-release:
	docker push $(IMAGE_NAME):release
