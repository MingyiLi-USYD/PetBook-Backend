pipeline {
    agent {
        node {
            label 'jdk11'
        }

    }
    stages {
        stage('打印') {
            agent none
            steps {
                echo "SERVICE 参数的值是: ${params.SERVICE}"
            }
        }

        stage('拉取代码') {
            agent none
            steps {
                container('maven') {
                    git(url: 'https://github.com/MingyiLi-USYD/PetBook-Backend.git', credentialsId: 'username-token', branch: 'cicd', changelog: true, poll: false)
                    sh 'ls'
                }

            }
        }

        stage('打包项目') {
            agent none
            steps {
                container('maven') {
                    sh 'ls'
                    sh 'mvn clean package -Dmaven.test.skip=true'
                }

            }
        }

        stage('构建镜像') {
            when {
                expression {
                    !params.SKIP_PUSH
                }

            }
            parallel {
                stage('构建gateway') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-gateway9257'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t gateway:latest -f cloud-gateway9257/Dockerfile cloud-gateway9257/'
                        }

                    }
                }

                stage('构建uaa') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-uaa7000'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t uaa:latest -f cloud-uaa7000/Dockerfile cloud-uaa7000/'
                        }

                    }
                }

                stage('构建socket-service-provider') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-socket-service-provider12800'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t socket-service-provider:latest -f cloud-socket-service-provider12800/Dockerfile cloud-socket-service-provider12800/'
                        }

                    }
                }

                stage('构建object-storage-service') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-object-storage-service12900'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t object-storage-service:latest -f cloud-object-storage-service12900/Dockerfile cloud-object-storage-service12900/'
                        }

                    }
                }

                stage('构建user-service-provider') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-user-service-provider12100'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t user-service-provider:latest -f cloud-user-service-provider12100/Dockerfile cloud-user-service-provider12100/'
                        }

                    }
                }

                stage('构建user-service-base-provider') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-user-service-base-provider12101'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t user-service-base-provider:latest -f cloud-user-service-base-provider12101/Dockerfile cloud-user-service-base-provider12101/'
                        }

                    }
                }


                stage('构建user-service-consumer') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-user-service-consumer11100'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t user-service-consumer:latest -f cloud-user-service-consumer11100/Dockerfile cloud-user-service-consumer11100/'
                        }

                    }
                }

                stage('构建pet-service-provider') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-pet-service-provider12300'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t pet-service-provider:latest -f cloud-pet-service-provider12300/Dockerfile cloud-pet-service-provider12300/'
                        }

                    }
                }

                stage('构建post-service-provider') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-post-service-provider12200'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t post-service-provider:latest -f cloud-post-service-provider12200/Dockerfile cloud-post-service-provider12200/'
                        }

                    }
                }

                stage('构建friend-service-provider') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-friend-service-provider12400'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t friend-service-provider:latest -f cloud-friend-service-provider12400/Dockerfile cloud-friend-service-provider12400/'
                        }

                    }
                }

                stage('构建comment-service-provider') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-comment-service-provider12500'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t comment-service-provider:latest -f cloud-comment-service-provider12500/Dockerfile cloud-comment-service-provider12500/'
                        }

                    }
                }

                stage('构建interaction-service-provider') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-interaction-service-provider12600'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t interaction-service-provider:latest -f cloud-interaction-service-provider12600/Dockerfile cloud-interaction-service-provider12600/'
                        }

                    }
                }

                stage('构建post-service-consumer') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-post-service-consumer11200'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t post-service-consumer:latest -f cloud-post-service-consumer11200/Dockerfile cloud-post-service-consumer11200/'
                        }

                    }
                }

                stage('构建pet-service-consumer') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-pet-service-consumer11300'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t pet-service-consumer:latest -f cloud-pet-service-consumer11300/Dockerfile cloud-pet-service-consumer11300/'
                        }

                    }
                }

                stage('构建friend-service-consumer') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-friend-service-consumer11400'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t friend-service-consumer:latest -f cloud-friend-service-consumer11400/Dockerfile cloud-friend-service-consumer11400/'
                        }

                    }
                }

                stage('构建comment-service-consumer') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-comment-service-consumer11500'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t comment-service-consumer:latest -f cloud-comment-service-consumer11500/Dockerfile cloud-comment-service-consumer11500/'
                        }

                    }
                }

                stage('构建interaction-service-consumer') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-interaction-service-consumer11600'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t interaction-service-consumer:latest -f cloud-interaction-service-consumer11600/Dockerfile cloud-interaction-service-consumer11600/'
                        }

                    }
                }

                stage('构建chat-service-provider') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-chat-service-provider12700'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t chat-service-provider:latest -f cloud-chat-service-provider12700/Dockerfile cloud-chat-service-provider12700/'
                        }

                    }
                }

                stage('构建chat-service-consumer') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-chat-service-consumer11700'
                        }

                    }
                    steps {
                        container('maven') {
                            sh 'docker build -t chat-service-consumer:latest -f cloud-chat-service-consumer11700/Dockerfile cloud-chat-service-consumer11700/'
                        }

                    }
                }

            }
        }

        stage('default-3') {
            when {
                expression {
                     !params.SKIP_PUSH
                }

            }
            parallel {
                stage('推送object-storage-service镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-object-storage-service12900'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-io-registry' ,usernameVariable : 'DOCKER_USER_VAR' ,passwordVariable : 'DOCKER_PWD_VAR' ,)]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag object-storage-service:latest $REGISTRY/$DOCKERHUB_NAMESPACE/object-storage-service:latest'
                                sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/object-storage-service:latest'
                            }

                        }

                    }
                }

                stage('推送socket-service-provider镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-socket-service-provider12800'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId : 'docker-io-registry' ,usernameVariable : 'DOCKER_USER_VAR' ,passwordVariable : 'DOCKER_PWD_VAR' ,)]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag socket-service-provider:latest $REGISTRY/$DOCKERHUB_NAMESPACE/socket-service-provider:latest'
                                sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/socket-service-provider:latest'
                            }

                        }

                    }
                }

                stage('推送user-service-provider镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-user-service-provider12100'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag user-service-provider:latest $REGISTRY/$DOCKERHUB_NAMESPACE/user-service-provider:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/user-service-provider:latest'
                            }

                        }

                    }
                }

                stage('推送user-service-base-provider镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-user-service-base-provider12101'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag user-service-base-provider:latest $REGISTRY/$DOCKERHUB_NAMESPACE/user-service-base-provider:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/user-service-base-provider:latest'
                            }

                        }

                    }
                }


                stage('推送user-service-consumer镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-user-service-consumer11100'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag user-service-consumer:latest $REGISTRY/$DOCKERHUB_NAMESPACE/user-service-consumer:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/user-service-consumer:latest'
                            }

                        }

                    }
                }

                stage('推送pet-service-provider镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-pet-service-provider12300'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag pet-service-provider:latest $REGISTRY/$DOCKERHUB_NAMESPACE/pet-service-provider:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/pet-service-provider:latest'
                            }

                        }

                    }
                }

                stage('推送post-service-provider镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-post-service-provider12200'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag post-service-provider:latest $REGISTRY/$DOCKERHUB_NAMESPACE/post-service-provider:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/post-service-provider:latest'
                            }

                        }

                    }
                }

                stage('推送friend-service-provider镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-friend-service-provider12400'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag friend-service-provider:latest $REGISTRY/$DOCKERHUB_NAMESPACE/friend-service-provider:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/friend-service-provider:latest'
                            }

                        }

                    }
                }

                stage('推送comment-service-provider镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-comment-service-provider12500'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag comment-service-provider:latest $REGISTRY/$DOCKERHUB_NAMESPACE/comment-service-provider:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/comment-service-provider:latest'
                            }

                        }

                    }
                }

                stage('推送interaction-service-provider镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-interaction-service-provider12600'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag interaction-service-provider:latest $REGISTRY/$DOCKERHUB_NAMESPACE/interaction-service-provider:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/interaction-service-provider:latest'
                            }

                        }

                    }
                }

                stage('推送post-service-consumer镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-post-service-consumer11200'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag post-service-consumer:latest $REGISTRY/$DOCKERHUB_NAMESPACE/post-service-consumer:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/post-service-consumer:latest'
                            }

                        }

                    }
                }

                stage('推送pet-service-consumer镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-pet-service-consumer11300'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag pet-service-consumer:latest $REGISTRY/$DOCKERHUB_NAMESPACE/pet-service-consumer:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/pet-service-consumer:latest'
                            }

                        }

                    }
                }

                stage('推送friend-service-consumer镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-friend-service-consumer11400'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag friend-service-consumer:latest $REGISTRY/$DOCKERHUB_NAMESPACE/friend-service-consumer:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/friend-service-consumer:latest'
                            }

                        }

                    }
                }

                stage('推送comment-service-consumer镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-comment-service-consumer11500'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag comment-service-consumer:latest $REGISTRY/$DOCKERHUB_NAMESPACE/comment-service-consumer:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/comment-service-consumer:latest'
                            }

                        }

                    }
                }

                stage('推送interaction-service-consumer镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-interaction-service-consumer11600'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag interaction-service-consumer:latest $REGISTRY/$DOCKERHUB_NAMESPACE/interaction-service-consumer:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/interaction-service-consumer:latest'
                            }

                        }

                    }
                }

                stage('推送chat-service-provider镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-chat-service-provider12700'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag chat-service-provider:latest $REGISTRY/$DOCKERHUB_NAMESPACE/chat-service-provider:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/chat-service-provider:latest'
                            }

                        }

                    }
                }

                stage('推送chat-service-consumer镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-chat-service-consumer11700'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag chat-service-consumer:latest $REGISTRY/$DOCKERHUB_NAMESPACE/chat-service-consumer:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/chat-service-consumer:latest'
                            }

                        }

                    }
                }

                stage('推送gateway镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-gateway9257'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag gateway:latest $REGISTRY/$DOCKERHUB_NAMESPACE/gateway:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/gateway:latest'
                            }

                        }

                    }
                }

                stage('推送uaa镜像') {
                    agent none
                    when {
                        expression {
                            return params.SERVICE == 'all' || params.SERVICE == 'cloud-uaa7000'
                        }

                    }
                    steps {
                        container('maven') {
                            withCredentials([usernamePassword(credentialsId: 'docker-io-registry', usernameVariable: 'DOCKER_USER_VAR', passwordVariable: 'DOCKER_PWD_VAR')]) {
                                sh 'echo "$DOCKER_PWD_VAR" | docker login $REGISTRY -u "$DOCKER_USER_VAR" --password-stdin'
                                sh 'docker tag uaa:latest $REGISTRY/$DOCKERHUB_NAMESPACE/uaa:latest'
                                sh 'docker push $REGISTRY/$DOCKERHUB_NAMESPACE/uaa:latest'
                            }

                        }

                    }
                }

            }
        }

        stage('deploy to dev') {
            agent none
            steps {
                script {
                    if (params.SERVICE == 'all') {
                        // 部署所有服务
                        def services = [
                                'cloud-gateway9257',
                                'cloud-uaa7000',
                                'cloud-user-service-base-provider12101',
                                'cloud-socket-service-provider12800',
                                'cloud-user-service-provider12100',
                                'cloud-user-service-consumer11100',
                                'cloud-pet-service-provider12300',
                                'cloud-post-service-provider12200',
                                'cloud-friend-service-provider12400',
                                'cloud-comment-service-provider12500',
                                'cloud-interaction-service-provider12600',
                                'cloud-post-service-consumer11200',
                                'cloud-pet-service-consumer11300',
                                'cloud-friend-service-consumer11400',
                                'cloud-comment-service-consumer11500',
                                'cloud-interaction-service-consumer11600',
                                'cloud-chat-service-provider12700',
                                'cloud-chat-service-consumer11700',
                                'cloud-object-storage-service12900'
                        ]
                        deployServices(services)
                    } else {
                        // 部署单个服务
                        deployServices([params.SERVICE])
                    }
                }

            }
        }

    }
    environment {
        DOCKER_CREDENTIAL_ID = 'dockerhub-id'
        GITHUB_CREDENTIAL_ID = 'github-id'
        KUBECONFIG_CREDENTIAL_ID = 'demo-kubeconfig'
        REGISTRY = 'docker.io'
        DOCKERHUB_NAMESPACE = 'mingyiusyd'
        GITHUB_ACCOUNT = 'kubesphere'
        APP_NAME = 'devops-java-sample'
    }
    parameters {
        choice(name: 'SERVICE', choices: ['all','cloud-user-service-base-provider12101','cloud-object-storage-service12900','cloud-gateway9257', 'cloud-uaa7000', 'cloud-socket-service-provider12800', 'cloud-user-service-provider12100', 'cloud-user-service-consumer11100', 'cloud-pet-service-provider12300', 'cloud-post-service-provider12200', 'cloud-friend-service-provider12400', 'cloud-comment-service-provider12500', 'cloud-interaction-service-provider12600', 'cloud-post-service-consumer11200', 'cloud-pet-service-consumer11300', 'cloud-friend-service-consumer11400', 'cloud-comment-service-consumer11500', 'cloud-interaction-service-consumer11600', 'cloud-chat-service-provider12700', 'cloud-chat-service-consumer11700'], description: '请选择要部署的服务')
        booleanParam(name: 'SKIP_PUSH', defaultValue: false, description: '是否跳过推送')
    }
}

def deployServices(serviceList) {
    container('maven') {
        withCredentials([
                kubeconfigFile(credentialsId: env.KUBECONFIG_CREDENTIAL_ID, variable: 'KUBECONFIG')
        ]) {
            serviceList.each { service ->
                sh "envsubst < ${service}/deploy/deployment.yaml | kubectl apply -f -"
            }
        }
    }
}