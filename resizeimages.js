const sharp = require("sharp");
const fs = require("fs");
const path = require("path");

const inputFolder = "C:/Users/ACER/Desktop/NostraCasa Italian desing/imagenesPeq";
const outputFolder = "C:/Users/ACER/Desktop/NostraCasa Italian desing/imagenesGran";

function processFolder(folder, relativePath = "") {
  const items = fs.readdirSync(folder);

  items.forEach(item => {
    const inputPath = path.join(folder, item);
    const relativeItemPath = path.join(relativePath, item);
    const outputPath = path.join(
      outputFolder,
      relativeItemPath.replace(path.extname(item), ".webp")
    );

    const stat = fs.statSync(inputPath);

    if (stat.isDirectory()) {
      // Crear carpeta equivalente en salida
      fs.mkdirSync(path.join(outputFolder, relativeItemPath), { recursive: true });

      // Entrar dentro de la subcarpeta
      processFolder(inputPath, relativeItemPath);
    } else {
      // Es imagen → procesar
      sharp(inputPath)
        .resize({ width: 1600 })
        .webp({ quality: 80 })
        .toFile(outputPath)
        .then(() => {
          console.log("Optimizada:", relativeItemPath);
        })
        .catch(err => {
          console.error("Error con:", relativeItemPath, err);
        });
    }
  });
}

if (!fs.existsSync(outputFolder)) {
  fs.mkdirSync(outputFolder);
}

processFolder(inputFolder);

