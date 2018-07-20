#! /bin/bash
set -x

# Remove duplicates
cat /etc/resolv.conf | sed 's/tba-nifi.default.svc.cluster.local teamdigitale.test/\1/g' > /tmp/resolv.conf
cp /tmp/resolv.conf /etc/resolv.conf
# Two steps needed to avoid "Device or resource busy" error inside Docker
cat /etc/resolv.conf | sed "s/^search \(.*\)/search ${NIFI_ADDITIONAL_SEARCH_DOMAINS:-} \1/g" > /tmp/resolv.conf
cp /tmp/resolv.conf /etc/resolv.conf


# Update nifi properties

mkdir -p /usr/nifi/conf
cp --preserve=links /usr/nifi/conf.temp/* /usr/nifi/conf

nifi_props_file="/usr/nifi/conf/nifi.properties"
    prop_replace () {
  target_file=${3:-${nifi_props_file}}
  echo 'replacing target file ' ${target_file}
  sed -i -e "s|^$1=.*$|$1=$2|"  ${target_file}
}

HOSTNAME=$(hostname)
prop_replace 'nifi.web.http.host'               "$HOSTNAME"
prop_replace 'nifi.remote.input.host'           "$HOSTNAME"
prop_replace 'nifi.cluster.node.address'        "$HOSTNAME"