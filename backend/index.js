import express from "express";

const app = express();
const PORT = 3001;

app.get("/api", (req, res) => {
    res.json({ message: "Hello from server!" });
  });
  
app.get("/a", (req, res) =>{
    res.send()
})

  app.listen(PORT, () => {
    console.log(`Server listening on ${PORT}`);
  });