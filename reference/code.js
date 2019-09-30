
const empty_image = { "item": "hwell:empty" }
var imageSources = [];

function imgOnError(image) {
    image.onerror = "";

    if (image.src.indexOf("items") >= 0) {
        image.src = image.src.replace("items", "blocks");
    } else {
        image.src = getPath(empty_image.item, ".png", "/textures/");
    }
    return true;
}

function getdir(dir, path) {
    if (dir != undefined && "subdirectories" in dir) {

        var indexOf = path.indexOf("/");
        if (indexOf >= 0) {
            let prepath = path.substring(0, indexOf);
            let pospath = path.substring(indexOf + 1);
            // console.log(prepath + " -> " + pospath)
            let subdir = dir.subdirectories.filter(obj => {
                return obj.name === prepath;
            })[0];
            // console.log(subdir);
            return getdir(subdir, pospath);
        }

        //--//--//--//--//--//--//--//--//--//--//--//--//

        let subdir = dir.subdirectories.filter(obj => {
            return obj.name === path;
        })[0];
        if (subdir != undefined)
            return subdir;
    }
    return dir;
}

function getNameOfFile(path) {
    let nameWithExt = path.substring(path.lastIndexOf("/") + 1)
    return nameWithExt.substring(0, nameWithExt.lastIndexOf("."));
}

$(document).ready(function () {
    $.getJSON("listdir.json", function (listdir) {
        let categories = getdir(listdir, "assets/hwell/patchouli_books/book_of_the_well/en_us/categories");
        let entries = getdir(listdir, "assets/hwell/patchouli_books/book_of_the_well/en_us/entries");
        let cats = categories.files.map(catfile => {
            catName = getNameOfFile(catfile);
            return {
                "name": catName,
                "entries": getdir(entries, catName).files.map(entryFile => {
                    return getNameOfFile(entryFile);
                })
            };
        });
        recipesCategory(getdir(listdir, "assets/hwell/recipes"));
        cats.forEach(cat => {
            category(cat.name, cat.entries);
        });
    });
});
function recipesCategory(recipesdir) {

    $('<div class="tablink" onclick="changeTab(event, \'recipes\')">Recipes</div>')
        .appendTo("#tablinks");

    // var basepath = "recipes/";
    var cat_div = $("<div class='tab' id='recipes'></div>");

    // let recipe_data = {
    //     "type": "minecraft:crafting_shaped",
    //     "pattern": [
    //         "ABA",
    //         "B B",
    //         "ABA"],
    //     "key": {
    //         "A": [{ "item": "hwell:asul_ingot" }, { "item": "hwell:heavy_ingot" }],
    //         "B": { "item": "minecraft:stone", "data": 0 }
    //     },
    //     "result": { "item": "hwell:asul_machine_case" }
    // };


    recipesdir.files.forEach(path => {
        // console.log(path);
        // console.log(getFileName(path));
        $.getJSON("assets/hwell/recipes/" + getFileName(path), function (recipe_data) {
            console.log(recipe_data);
            makeRecipeDiv(recipe_data).appendTo(cat_div);
        });
    });
    cat_div.appendTo("#tabs");
}
function category(name, elems) {
    var cat_div = $("<div class='tab' id='" + name + "'></div>");
    var basepath = "assets/hwell/patchouli_books/book_of_the_well/en_us/";

    $.getJSON(basepath + "categories/" + name + ".json", function (cat_data) {
        $('<div class="tablink" onclick="changeTab(event, \'' + name + '\')">' + cat_data.name + '</div>')
            .appendTo("#tablinks");
    });

    elems.forEach(element => {
        $.getJSON(basepath + "entries/" + name + "/" + element + ".json", function (elem_data) {
            var entry_div = $("<div class='entry_div'></div>");
            $('<h3>' + element + '</h3>').appendTo(entry_div);
            elem_data.pages.forEach(page => {
                if ("text" in page) {
                    $('<p>' + replaceStuff(page.text) + '</p>').appendTo(entry_div);
                }
                if ("images" in page) {
                    page.images.forEach(resource => {
                        var imagePath = getPath(resource);
                        $("<img onerror='imgOnError(this)' src='" + imagePath + ">" + imagePath + "<img>").appendTo(entry_div);
                    });
                }
            });
            entry_div.appendTo(cat_div);
        });
    });
    cat_div.appendTo("#tabs");
}
function replaceStuff(text) {
    return text
        .split("$(br)").join("<br>")
        .split("$(br2)").join("<br><br>")
        .split("$(li)").join("<br>&nbsp;&nbsp;&nbsp;&nbsp;&bull;&nbsp;");
}

function changeTab(evt, name) {
    var i, tab, tablinks;
    tab = document.getElementsByClassName("tab");
    for (i = 0; i < tab.length; i++) { tab[i].style.display = "none"; }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    document.getElementById(name).style.display = "block";
    evt.currentTarget.className += " active";
}

function makeRecipeDiv(recipe_data) {
    recipeDiv = $("<div class='recipe_div'></div>");
    table = $("<div class='recipe_table'></div>");
    if (recipe_data.type == "minecraft:crafting_shaped") {
        var i = 1;
        recipe_data.pattern.forEach(element => {
            for (var l = 0; l < element.length; l++) {
                if (element.charAt(l) != " ") {
                    let content = readFromKey(recipe_data.key, element.charAt(l));
                    content.appendTo(table);
                    // table.find("#" + i).html(content);
                } else {
                    $("<img onerror='imgOnError(this)' width='32px' height='32px' src='"
                        + getPath(empty_image.item, ".png", "/textures/items/") + "'></img>").appendTo(table);
                }
                i++;
            }
        });
    }
    table.appendTo(recipeDiv);
    img = createStoppedImage(getPath(recipe_data.result.item, ".png", "/textures/blocks/"));
    img.appendTo(recipeDiv);
    return recipeDiv;
}
function readFromKey(key, char) {
    let obj = key[char];
    if (Array.isArray(obj)) {
        let images = obj.map(item => {
            return getPath(item.item, ".png", "/textures/items/");
        });
        return createChangingImage(images);
    } else {
        return createStoppedImage(getPath(obj.item, ".png", "/textures/items/"));
    }
}

// function getItemImage(itemObj) {
//     return "assets/" + itemObj.item.substring(0, itemObj.item.indexOf(":")) + "/textures/items/" + getImagePathFromResource(itemObj.item);
// }
// function a(resource) {
//     return getFilePathFromResource(resource) + ".png";
// }
// function b(resource) {
//     let path = getFilePathFromResource(resource);
//     return path;
// }

function getPath(resource, ext = ".png", subpath = "/") {
    left = resource.substring(0, resource.indexOf(":"));
    right = resource.substring(resource.indexOf(":") + 1);
    return "assets/" + left + subpath + right + ext;
}

function getFileName(path) {
    return path.substring(path.lastIndexOf("/"));
}

function createStoppedImage(image) {
    return $("<img onerror='imgOnError(this)' width='32px' height='32px' src='" + image + "'>");
    // return $("<img width='32px' height='32px' >");
}

var nextId = 0;
function createChangingImage(images) {
    let id = "image_" + nextId;
    nextId++;
    imageSources.push({ "id": id, "i": 0, "images": images });
    image = $("<img onerror='imgOnError(this)' width='32px' height='32px' id='" + id + "' src='" + images[0] + "'>");
    // image = $("<img width='32px' height='32px' id='" + id + ">");
    return image;
}

setInterval(() => {
    imageSources.forEach(element => {
        element.i++;
        if (element.i == element.images.length)
            element.i = 0;
        $("#" + element.id).attr('src', element.images[element.i]);
        // console.log(element.id);
    });
}, 1000);

