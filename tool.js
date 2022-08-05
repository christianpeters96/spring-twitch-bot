/*global process*/
const args = process.argv;
for (let i = 0; i < 2; i++) args.shift();
if (args.length === 0 || typeof args[0] !== "string") showUsage();

const webDir = "./src/main/resources/static/";
const i18nDir = webDir + "i18n/";
const i18nFiles = ['de.json', 'us.json'];

if (args[0].toLowerCase() === 'check_i18n') {
    let i18nMap = {};
    for (let file of i18nFiles) i18nMap[file] = { keys: [] };
    for (let file of Object.keys(i18nMap)) {
        iterateObjectKeys(require(i18nDir + file), function (key) {
            i18nMap[file].keys.push(key);
        });
    }
    console.log(compareLanguageKeys(i18nMap, "de.json", "us.json"));
}
else showUsage();

function iterateObjectKeys(object, callback, prepend) {
    if (typeof prepend !== "string") prepend = "";
    if (prepend.length !== 0 && !/\.$/.test(prepend)) prepend += ".";
    for (let key of Object.keys(object)) {
        if (typeof object[key] === "object") {
            iterateObjectKeys(object[key], callback, prepend + key)
        }
        else callback(prepend + key);
    }
}

function compareLanguageKeys(i18nMap, lang_file_1, lang_file_2) {
    let obj1 = i18nMap[lang_file_1].keys;
    let obj2 = i18nMap[lang_file_2].keys;
    let warn = [];
    if (obj1.length !== obj2.length) warn.push("The two objects does not have the same size ["+obj1.length+"<>"+obj2.length+"]");
    for (let key of obj1) {
        if (obj2.indexOf(key) === -1) {
            warn.push('Could not find key [in ' + lang_file_2 + ']: ' + key);
        }
    }
    for (let key of obj2) {
        if (obj1.indexOf(key) === -1) {
            warn.push('Could not find key [in ' + lang_file_1 + ']: ' + key);
        }
    }
    return warn;
}

function showUsage() {
    console.log("--------------------------------------------------");
    console.log();
    console.log("Usage: `node tool [Action]`");
    console.log();
    console.log("Actions:");
    console.log("  `check_i18n`");
    console.log();
    console.log("--------------------------------------------------");
    process.exit(0);
}