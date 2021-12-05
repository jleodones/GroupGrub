window.onload = (() => {
    const byId = id => document.querySelector(`#${id}`); //selects by ID
    const dealTag = byId("goodList"); //creates a list for deals
    const nodealTag = byId("badList"); //creates a list const for noDeals

    const appendListItem = (theList, itemTxt) => { //append list item 
        let listItem = document.createElement("li"); //create a list item 
        listItem.textContent = itemTxt; //list item set to given text
        theList.appendChild(listItem);//adds to the list passed into the method(html)
    };

    const addGoodItem = () => { //adds items to list object
        const inputGood = byId("goodTag");
        
        const val = inputGood.value.trim();

        if (!val) { return alert("Please enter a good Yelp tag"); }

        if(goodTags.indexOf(val) === -1){ 
            goodTags.push(val);//fix so that if the tag is good it appears in the good list, else it appears in the bad list
            appendListItem(dealTag, val);
            inputGood.value = "";
        }else{
            return alert("Oops! No duplicates!");
        }
    };

    const addBadItem = () => { //adds items to list object
        const inputBad = byId("badTag");
        
        const val = inputBad.value.trim();

        if (!val) { return alert("Please enter a bad Yelp tag"); }

        if(badTags.indexOf(val) === -1){ 
            badTags.push(val);//fix so that if the tag is good it appears in the good list, else it appears in the bad list
            appendListItem(nodealTag, val);
            inputBad.value = "";
        }else{
            return alert("Oops! No duplicates!");//if there is a duplicate tag it will not be added
        }
    };
    
    // create good tags list
    let goodTags = [];
    goodTags.forEach(item => appendListItem(dealTag, item));

    let badTags = [];
    badTags.forEach(item => appendListItem(nodealTag, item));
        
    // add button handling
    byId("addGItem").addEventListener("click", addGoodItem);
    byId("addBItem").addEventListener("click", addBadItem);
})();