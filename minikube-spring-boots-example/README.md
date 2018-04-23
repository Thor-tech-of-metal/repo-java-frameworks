# Minions for minikube

I would like to take this opportunity to say thanks to Ryan Dawson for posting the tutorial mentioned bellow.
In This read me file I am going to add some extra information related to the tutorial mentioned before

https://dzone.com/articles/minions-in-minikube-a-kubernetes-intro-for-java-de


# install minikube all steps for fedora. 

0 virtual box 

    https://www.if-not-true-then-false.com/2010/install-virtualbox-with-yum-on-fedora-centos-red-hat-rhel/

1 install docker
    
    https://docs.docker.com/install/linux/docker-ce/fedora/

2 install kubectl
    
    https://kubernetes.io/docs/tasks/tools/install-kubectl/

3 install 
    
    https://github.com/kubernetes/minikube/releases

## Aims

To make an army of minions in minikube in each one per VM.


## How to Run minikube

Start minikube:
 
`minikube start --memory 4000 --cpus 3`

## Build docker images and deploy pods.

Build minion image for minikube - from this directory run
1) create docker image for local docker.

`eval $(minikube docker-env)` <br/>
`docker build . -t local/minion`

Remeber that in your pods definition file minion-army.yml we have something llike that:

`name: happy-minionimage: local/minion:latest imagePullPolicy: IfNotPresent`

It means that it will pull from the docker images the images called local/minion:latest. if it is not present.

2) Deploy the army
 
`kubectl create -f minion-army.yml`

3) To see the troop types: 

`open http://$(minikube ip):30080` <br/>
`open http://$(minikube ip):30081` <br/>
`open http://$(minikube ip):30082` <br/>
`open http://$(minikube ip):30083` <br/>

## See all deployed pods

`kubectl get pods`

## Check the dashboard


`minikube dashboard`

To create more troops of each type do `minikube dashboard` and go to the deployment and change the number of replicas

## One Minion Falls, Another Takes His Place

Go to the minikube dashboard and check the pods table and select any pod to kill it. 
For instance: one-eyed-minion-65b68975b4-j9jbd

`kubectl delete pod  one-eyed-minion-65b68975b4-j9jbd`

Refresh the browser (it will take a little while) and see that another pod is already there

## Minion Upgrades

Change the version in the Controller class to 0.2. Do:

`docker build . -t local/minion:0.2`

Then open minion-army.yml and find-replace all the "latest" with "0.2". Save the changes and do:

`kubectl apply -f minion-army.yml --record`

Refresh the browser of one of the minion types to see the version change in line with what you see from 

`kubectl rollout status deployment <deployment_name>` 

where <deployment_name> is the name defined in the pod file minion-army.yml.

## Minion Rollbacks

To see the history of what was deployed do 

`kubectl rollout history deployment <deployment_name>` 

and to rollback do 

`kubectl rollout undo deployment <deployment_name> --to-revision=1` 

(can take a little while)

## To Destroy the Army

`kubectl delete -f minion-army.yml`

Stop minikube with `minikube stop`
