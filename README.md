# BH-Club ticker software

## Instructions

Run with root permissions to be able to kill the currently running output or to be able to replace it

## Install Prerequisites

- install raspian lite
- configure wifi and setup ssh to start on boot (e.g. via raspi-config)
- install software
  - sudo apt update
  - sudo apt install -y --force-yes git openjdk-9-jre python2.7-dev python-pil python3-dev libgraphicsmagick++-dev libwebp-dev
  - git clone https://github.com/hzeller/rpi-rgb-led-matrix.git
  - cd rpi-rgb-led-matrix
  - make
  - chown -R $SUDO_USER:$(id -g $SUDO_USER) \`pwd\`
  - cd utils
  - make led-image-viewer
- disable audio (because of hardware mod)
  - in /boot/config.txt set:  dtparam=audio=off

## "Install"
- copy bh-ticker.jar to the pi
- copy bh-ticker.service to the pi and move it into `/etc/systemd/system`
- add executable flag `sudo chmod +x /etc/systemd/system/bh-ticker.service`
- run `sudo systemctl start bh-ticker.service`
- enable autostart `sudo systemctl enable bh-ticker.service`
- enable daily restart `sudo nano /etc/cron.d/reboot` and copy content from `cronjob` file
- scrolling text
  - sudo apt install otf2bdf
  - wget https://github.com/dharmatype/Bebas-Neue/raw/master/fonts/otf/BebasNeue-Regular.otf
  - mkdir fonts
  - otf2bdf -p 27 BebasNeue-Regular.otf > fonts/BebasNeue-Regular.bdf
 
## TODO-List
  * install / update script .sh basteln
  * start-skript bauen
    * nice command
    * GC umstellen auf weniger blocking
  * LED Slowdown konfigurierbar machen
  * Einträge kopierbar (damit quasi bearbeitbar)
  * neue Einträge via Modal-Fenster hinzufügen können
  * Playlist zwischen Text und Image kombinierbar machen
  * Bilder mit korrektem mimetype ausliefern
  * Datenbankmigrationsskript
  * Auf Startseite anzeigen, was gerade läuft
  * Uhrzeit im Scrolltext anzeigen können
  * Playlist Einträge verschiebbar
    * Einträge löschen können
    * Temporäre Playlisten (für einen Abend) erstellen können
  * RT-Kernel probieren
    * https://lemariva.com/blog/2018/07/raspberry-pi-preempt-rt-patching-tutorial-for-kernel-4-14-y
    * RPI Skripte mit unnice starten
    * mindestens eine CPU reservieren für RPI skripte

### Playing
  * einlassstopsignal abgreifen -> aktion triggern (z.B. andere sachen playen)
    * ggf. REST-API dafür anbieten
