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


