#AndroidSMSRetriever

To install plugin in cordova or ionic run :

ionic cordova plugin add https://github.com/rajanmodi30/google-retriever-api-cordova.git

For the ionic make provider file with 

@Plugin({
  pluginName: 'AndroidSmsRetriever', // should match the name of the wrapper class
  plugin: 'androidsmsretriever', // NPM package name
  pluginRef: 'AndroidSmsRetriever', // name of the object exposed by the plugin
  repo: 'https://github.com/rajanmodi30/google-retriever-api-cordova', // plugin repository URL
  platforms: ['Android'] // supported platforms
})


and in the costructor 


  @Cordova()
  getAppHash(): Promise<string> {
    return;
  }


//the above will return the hash of the app
  @Cordova()
  onSmsReceived(): Promise<string> {
    return;
  }

//the abouve function will return the sms 


Now call this provider functions in any page with 
this.provider.onSmsReceived()
.then((message)=>{
console.log("message",message);
}).catch(e=>console.log(e));


this.provider.getAppHash()
.then((message)=>{
console.log("message",message);
}).catch(e=>console.log(e));


