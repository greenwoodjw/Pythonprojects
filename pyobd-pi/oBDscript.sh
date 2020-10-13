#!/bin/bash
bluetoothctl <<EOF
connect 00:04:3E:30:EC:58
trust 00:04:3E:30:EC:58
EOF
sudo rfcomm bind /dev/rfcomm0 00:04:3e:30:EC:58
