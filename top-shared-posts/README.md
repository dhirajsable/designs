# Top Shared Post Design

### How to setup Docker
#### Create a .env file in the same directory as your docker-compose.yml and define the variable:
```
USER_PROJECT_PATH=/Users/<your-username>/projects
```
#### Ensure the Path Exists
```
mkdir -p /Users/$(whoami)/projects/docker/zookeeper-data
mkdir -p /Users/$(whoami)/projects/docker/redis_data
mkdir -p /Users/$(whoami)/projects/docker/kafka-data
```

## API USAGE
### Share Post and Track it
```
curl -X POST -H "Content-Type: application/json" -d '{"postId":"123"}' http://localhost:8080/api/share
```
### Fetch top 10 posts for a specific interval:
```
curl -X GET "http://localhost:8080/leaderboard/day/2024-12-09?topN=10"
```