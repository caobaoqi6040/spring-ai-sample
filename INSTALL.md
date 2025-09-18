# nginx-proxy-manager

- https://nginxproxymanager.com/
- https://github.com/xiaoxinpro/nginx-proxy-manager-zh

```shell
name: nginx-proxy-manager
services:
  app:
    image: 'chishin/nginx-proxy-manager-zh:release'
    restart: always
    ports:
      - '80:80'
      - '81:81'
      - '443:443'
    volumes:
      - ./data:/data
      - ./letsencrypt:/etc/letsencrypt
```

```shell
sudo vim /etc/hosts
# nginx proxy manager test
127.0.0.1       proxy.ikun.com
127.0.0.1       ikun.com

# resolvectl for ubuntu 24
sudo resolvectl flush-caches
sudo systemctl restart systemd-resolved
sudo resolvectl statistics

nslookup proxy.ikun.com
```
