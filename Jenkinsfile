pipeline {
    agent {
        docker {
            image 'maven:eclipse-temurin'
            reuseNode true
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    environment {
        PROJECT_NAME = 'jk_test1'
        JAR_PATH = "target/${PROJECT_NAME}-*.jar"
        BUILD_RECORD_DIR = 'build-records'
        // 获取Git分支名称
        BRANCH_NAME = sh(script: 'git rev-parse --abbrev-ref HEAD', returnStdout: true).trim()
    }

    stages {
        stage('拉取代码') {
            steps {
                cleanWs()
                git url: 'https://github.com/Lunaticzy/jk_test',
                        branch: 'main'
            }
        }

        stage('代码编译与打包') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
            post {
                success {
                    sh "if [ ! -f ${JAR_PATH} ]; then exit 1; fi"
                    echo "构建成功，产物路径：${JAR_PATH}"
                }
            }
        }

        stage('记录构建信息') {
            steps {
                sh '''
                    mkdir -p ${BUILD_RECORD_DIR}

                    # 强制使用UTF-8编码
                    export LANG="en_US.UTF-8"
                    export LC_ALL="en_US.UTF-8"

                    # 使用Jenkins环境变量
                    echo "构建编号: ${BUILD_NUMBER}" > ${BUILD_RECORD_DIR}/build-info.txt
                    echo "构建时间: $(date +'%Y-%m-%d %H:%M:%S')" >> ${BUILD_RECORD_DIR}/build-info.txt
                    echo "Git分支: ${BRANCH_NAME}" >> ${BUILD_RECORD_DIR}/build-info.txt
                    echo "Git提交ID: $(git rev-parse HEAD)" >> ${BUILD_RECORD_DIR}/build-info.txt
                    echo "产物名称: $(basename ${JAR_PATH})" >> ${BUILD_RECORD_DIR}/build-info.txt
                    echo "产物大小: $(du -h ${JAR_PATH} | awk '{print $1}')" >> ${BUILD_RECORD_DIR}/build-info.txt

                    cp ${JAR_PATH} ${BUILD_RECORD_DIR}/
                '''
            }
        }

        stage('归档构建产物与记录') {
            steps {
                archiveArtifacts artifacts: "${BUILD_RECORD_DIR}/**", fingerprint: true
            }
        }
    }

    post {
        success {
            echo "构建完成，产物和构建信息已归档"
        }
        failure {
            echo "构建失败，请检查日志"
        }
    }
}