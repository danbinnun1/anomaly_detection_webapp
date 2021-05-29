export function convertCSVToJSON(file) {
  return new Promise((resolve) => {
    var reader = new FileReader();

    reader.onload = () => {
      let lines = reader.result.split("\n");
      let lastLine = lines[lines.length - 1];
      if (lastLine === '') {
        lines.pop();
      }
      let result = {};

      let headers = lines[0].split(",");
      for (let i = 0; i < headers.length; ++i) {
        result[headers[i]] = [];
      }

      for (let i = 1; i < lines.length; ++i) {
        let currentline = lines[i].split(",");

        for (let j = 0; j < headers.length; ++j) {
          result[headers[j]].push(currentline[j]);
        }
      }

      resolve(result);
    }

    reader.readAsText(file);
  });
}

export function convertJSONToLines(json) {
  if (json.length === 0) {
    return [];
  }
  
  let lines = [];

  lines.push(Object.keys(json));

  for (let i = 1; i <= Object.values(json)[0].length; ++i) {
    lines[i] = [];
  }

  lines[0].forEach(key => {
    const values = json[key];

    for (let j = 1; j <= values.length; ++j) {
      lines[j].push(values[j]);
    }
  });

  return lines;
}

export function splitCSV(file) {
  return new Promise((resolve) => {
    var reader = new FileReader();

    reader.onload = () => {
      let lines = reader.result.split("\n");
      let lastLine = lines[lines.length - 1];

      if (lastLine === '') {
        lines.pop();
      }

      let result = [];
      for (let i = 0; i < lines.length; ++i) {
        result.push(lines[i].split(","));
      }

      resolve(result);
    }
    
    reader.readAsText(file);
  });
}