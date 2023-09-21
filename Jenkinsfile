pipeline {
  agent {
    node {
      label 'jdk11'
    }

  }
  stages {
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
       parallel {
            stage('构建gateway') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t gateway:latest -f cloud-gateway9257/dockerfile cloud-gateway9257/'
                    }
                }
            }

            stage('构建uaa') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t uaa:latest -f cloud-uaa7000/dockerfile cloud-uaa7000/'
                    }
                }
            }


            stage('构建socket-service-provider') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t socket-service-provider:latest -f cloud-socket-service-provider12800/dockerfile cloud-socket-service-provider12800/'
                    }
                }
            }

            stage('构建user-service-provider') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t user-service-provider:latest -f cloud-user-service-provider12100/dockerfile cloud-user-service-provider12100/'
                    }
                }
            }

            stage('构建user-service-consumer') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t user-service-consumer:latest -f cloud-user-service-consumer11100/dockerfile cloud-user-service-consumer11100/'
                    }
                }
            }

            stage('构建pet-service-provider') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t pet-service-provider:latest -f cloud-pet-service-provider12300/dockerfile cloud-pet-service-provider12300/'
                    }
                }
            }

            stage('构建post-service-provider') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t post-service-provider:latest -f cloud-post-service-provider12200/dockerfile cloud-post-service-provider12200/'
                    }
                }
            }
            stage('构建friend-service-provider') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t friend-service-provider:latest -f cloud-friend-service-provider12400/dockerfile cloud-friend-service-provider12400/'
                    }
                }
            }

            stage('构建comment-service-provider') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t comment-service-provider:latest -f cloud-comment-service-provider12500/dockerfile cloud-comment-service-provider12500/'
                    }
                }
            }

            stage('构建interaction-service-provider') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t interaction-service-provider:latest -f cloud-interaction-service-provider12600/dockerfile cloud-interaction-service-provider12600/'
                    }
                }
            }

            stage('构建post-service-consumer') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t post-service-consumer:latest -f cloud-post-service-consumer11200/dockerfile cloud-post-service-consumer11200/'
                    }
                }
            }

            stage('构建pet-service-consumer') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t pet-service-consumer:latest -f cloud-pet-service-consumer11300/dockerfile cloud-pet-service-consumer11300/'
                    }
                }
            }
            stage('构建friend-service-consumer') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t friend-service-consumer:latest -f cloud-friend-service-consumer11400/dockerfile cloud-friend-service-consumer11400/'
                    }
                }
            }

            stage('构建comment-service-consumer') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t comment-service-consumer:latest -f cloud-comment-service-consumer11500/dockerfile cloud-comment-service-consumer11500/'
                    }
                }
            }

            stage('构建interaction-service-consumer') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t interaction-service-consumer:latest -f cloud-interaction-service-consumer11600/dockerfile cloud-interaction-service-consumer11600/'
                    }
                }
            }

            stage('构建chat-service-provider') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t chat-service-provider:latest -f cloud-chat-service-provider12700/dockerfile cloud-chat-service-provider12700/'
                    }
                }
            }

            stage('构建chat-service-consumer') {
                agent none
                steps {
                    container('maven') {
                        sh 'docker build -t chat-service-consumer:latest -f cloud-chat-service-consumer11700/dockerfile cloud-chat-service-consumer11700/'
                    }
                }
            }


       }


   }
    stage('default-3') {
      parallel {
            stage('推送socket-service-provider镜像') {
              agent none
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

            stage('推送user-service-consumer镜像') {
                agent none
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
      steps {
        container('maven') {
          input(id: 'deploy-to-dev', message: 'deploy to dev?')
          withCredentials([kubeconfigContent(credentialsId : 'KUBECONFIG_CREDENTIAL_ID' ,variable : 'KUBECONFIG_CONFIG' ,)]) {
            sh 'mkdir -p ~/.kube/'
            sh 'echo "$KUBECONFIG_CONFIG" > ~/.kube/config'
            sh 'envsubst < deploy/dev-ol/deploy.yaml | kubectl apply -f -'
          }

        }

      }
    }

    stage('deploy to production') {
      agent none
      steps {
        kubernetesDeploy(enableConfigSubstitution: true, deleteResource: false, kubeconfigId: "$KUBECONFIG_CREDENTIAL_ID", configs: 'deploy/**', dockerCredentials: [])
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
    string(name: 'TAG_NAME', defaultValue: '', description: '')
  }
}