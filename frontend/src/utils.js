export default function convertCSVToJSON (file) {
  return new Promise ((resolve) => {
    var reader = new FileReader();

    reader.onload = () => {
      var lines = reader.result.split("\n");
      var result = {};
    
      var headers = lines[0].split(",");
      for (var i = 0; i < headers.length; ++i) {
        result[headers[i]] = [];
      }
    
      for (var i = 1; i < lines.length; ++i){
        var currentline = lines[i].split(",");
    
        for (var j = 0; j < headers.length; ++j) {
          result[headers[j]].push(currentline[j]);
        }
      }
      console.log(JSON.stringify(result))
      resolve(JSON.stringify(result));
    }

    reader.readAsText(file);
  });
}