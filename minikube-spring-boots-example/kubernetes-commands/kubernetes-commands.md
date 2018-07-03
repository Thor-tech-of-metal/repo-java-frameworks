# install minikube all steps for fedora. 

0 virtual box 

    https://www.if-not-true-then-false.com/2010/install-virtualbox-with-yum-on-fedora-centos-red-hat-rhel/

1 install docker
    
    https://docs.docker.com/install/linux/docker-ce/fedora/

2 install kubectl
    
    https://kubernetes.io/docs/tasks/tools/install-kubectl/

3 install 
    
    https://github.com/kubernetes/minikube/releases

### How to Run minikube

Start minikube:
 
`minikube start --memory 4000 --cpus 3`

### Build local docker images.

1) Go the docker image directory <br>
2) Run: <br>
`eval $(minikube docker-env)` <br/>
`docker build . -t local/minion`

Remeber that in your pods definition file service-mio.yml we have something llike that:

`name: happy-minionimage: local/minion:latest imagePullPolicy: IfNotPresent`

It means that it will pull from the docker images the images called local/minion:latest. if it is not present.

### Deploy pods in kubernetes.
1) Go the directory which contains the *.yml file with all pods <br>
2) Run: <br>
 
`kubectl create -f service-mio.yml`


### To Undeploy

`kubectl delete -f service-mio.yml`

Stop minikube with `minikube stop`

### See all deployed pods.

`kubectl get pods`

### See all system deployed pods.

`kubectl get po -n kube-system`

### Check the dashboard.

`minikube dashboard`

### Delete a deployed pod.

Go to the minikube dashboard and check the pods table and select any pod to kill it. 
For instance: one-eyed-minion-65b68975b4-j9jbd

`kubectl delete pod  one-eyed-minion-65b68975b4-j9jbd`

Refresh the browser (it will take a little while) and see that another pod is already there

### Update a deployed pod and pull a new image. 

1) Publish your changes  in the docker image. <br>
`docker build . -t local/minion:0.2`
2) Change your *.ylm file find-replace all the "latest" with "0.2".

3) Apply your changes. <br>
`kubectl apply -f service-mio.yml --record`

4) Deploy changes <br>
`kubectl rollout status deployment <deployment_name>` 

where <deployment_name> is the name defined in the pod file *.yml.

### Rollbacks to previous version.

To see the history of what was deployed use the command mentioned bellow and get the revision version that you wish to rollback. 

`kubectl rollout history deployment <deployment_name>` 

Rollback to revision 1. 

`kubectl rollout undo deployment <deployment_name> --to-revision=1` 

### Hot to get the current service host and ports
`export PORT=$(kubectl get --namespace crypt -o jsonpath="{.spec.ports[0].nodePort}" services crypt)
export HOST=$(kubectl get nodes -o jsonpath="{.items[0].status.addresses[0].address}")
export SERVICE_ADDR=https://$HOST:$PORT; echo $SERVICE_ADDR`


### Problems starting minikube.
delete  ~/.minikube/machines
`cd rm -r ~/.minikube/machines`


# Commands

`kubectl --context [THE-CONTEXT] [your command]`

examples: https://kubernetes.io/docs/reference/kubectl/cheatsheet/

curso: https://eu.udacity.com/course/scalable-microservices-with-kubernetes--ud615

###commands


all commands follow this order kubectl --context [CONTEXT] -n [NAME_SPACE] get deployments

###Get pods services command

*) get all deployments

`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] get deployments`

*) get all pods deployments instaces.
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] get pods`

*) get all services running.
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] get services`

*) get all pods more information for a namespace.
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] get pods -o wide`

*) List all pods in the namespace, including uninitialized ones
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] get pods --include-uninitialized`

### Watch events

*) get all pods more information for a namespace.
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] get events --watch`


### Describe pod commands


*) 
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] describe pods [POD-NAME]`


### Logs command


*) get all logs  for one pod instance
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] logs [POD-NAME]`

*) get all logs for one pod instance with tail.
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] logs [POD-NAME] -f`

### Bash command


*) 
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] exec -it [POD-NAME] bash`

### Connect to mysql machine

*) 
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] exec -it [POD-NAME] bash`
*) 
`mysql -h mysql.cx.aurora.tescocloud.com -u [THE-NAMESPACE] -p`


`kubectl --context <context-name> -n [THE-NAMESPACE] exec -it <pod-name> bash`

### Ip commands


*) Get ExternalIPs of all nodes
`kubectl --context [THE-CONTEXT] -n [THE-NAMESPACE] get nodes -o jsonpath='{.items[*].status.addresses[?(@.type=="ExternalIP")].address}' `


### Check which nodes are ready
` $ JSONPATH='{range .items[*]}{@.metadata.name}:{range @.status.conditions[*]}{@.type}={@.status};{end}{end}' \
 && kubectl get nodes -o jsonpath="$JSONPATH" | grep "Ready=True" `




