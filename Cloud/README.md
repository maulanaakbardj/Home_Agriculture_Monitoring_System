# Cloud 
## Build instance on Console 

-Nav menu > Compute engine > VM instances>
-Create VM with name "capstone-server" put region and zone to "asia-southeast1" and "asia-southeast1-b"
-set series to "N1" and machine type to "n1-standard-1", set bootdisk to "ubuntu"
-set firewall to enable "Allow HTTP traffic"and "Allow HTTPS traffic" 
-Click create

## Build instance on command line
```
gcloud beta compute --project=capstone-314901 instances create capstone-server --zone=asia-southeast1-b --machine-type=n1-standard-1 --subnet=default --network-tier=PREMIUM --maintenance-policy=MIGRATE --service-account=915448750025-compute@developer.gserviceaccount.com --scopes=https://www.googleapis.com/auth/devstorage.read_only,https://www.googleapis.com/auth/logging.write,https://www.googleapis.com/auth/monitoring.write,https://www.googleapis.com/auth/servicecontrol,https://www.googleapis.com/auth/service.management.readonly,https://www.googleapis.com/auth/trace.append --tags=http-server,https-server --image=ubuntu-2004-focal-v20210603 --image-project=ubuntu-os-cloud --boot-disk-size=30GB --boot-disk-type=pd-balanced --boot-disk-device-name=capstone-server --no-shielded-secure-boot --shielded-vtpm --shielded-integrity-monitoring --reservation-affinity=any
```

##Deploy Server for IOT
-open SSH
-run:git clone https://github.com/semaralawa/flask-deployment
-run:cd flask-deployment/coba-capstone 
-run:cd FLASK_APP=flaskr FLASK_ENV=production flask run --host=0.0.0.0


