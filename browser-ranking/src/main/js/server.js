const connect = require('connect');
const path = require('path');
const serveStatic = require('serve-static');

connect().use(serveStatic(path.join(__dirname, '../../../build/resources/main/'))).listen(8080, () => console.log('Server running on 8080...'));
