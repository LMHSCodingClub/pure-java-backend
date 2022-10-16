const http = require('http')

http.createServer((req, res) => {
  /* handle http requests */
}).listen(PORT);

console.log(`Server running at http://127.0.0.1:${PORT}/`);