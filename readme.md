### PREREQUISITES

- JAVA >= 21

### HOW TO RUN IT
```shell
# 1. CLONE THIS REPO
git clone https://github.com/nerfi58/JsonValidator.git

# 2. ENTER REPO DIRECTORY
cd JsonValidator

# 3. INSTALL ALL DEPENDENCIES WITH MAVEN
mvnw clean install

# 4. RUN THIS WITH JAVA. IAMRolePolicy.json is the default json file which will be validated. 
# You can change this file by entering different file name
java -cp target\JsonValidator-1.0.jar org.example.Main IAMRolePolicy.json
```