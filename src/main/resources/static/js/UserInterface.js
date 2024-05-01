import * as Categories from "./Categories.js"
import * as Utility from "./Utility.js"
import * as Links from "./Links.js"

onload()



// ******** initialization ********

function onload() {
    fetchAndDisplayCatsLinks()
    displayEnterCat()
}



// **** category and link functions ****

async function subCreateLinkAndCat() {
    const cat = document.getElementById("cat").value
    const link = document.getElementById("link").value
    const url = document.getElementById("url").value

    const invalidInput = Utility.invalidInput(cat, link, url)
    if (invalidInput) {
        console.log("invalidInput: " + invalidInput)
        displayTempText(invalidInput, "above-enter-cat")
        return
    }

    const requestData = {
        category: cat,
        link: link,
        url: url
    }

    try {
        const response = await fetch(`/create-link-and-cat`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        })
        if (!response.ok) {
            const errorMessage = await response.text()
            throw new Error(errorMessage)
        }
        displayEnterCat()
        await fetchAndDisplayCatsLinks()
    } catch (error) {
        console.error("Error in subCreateLinkAndCat:", error)
        tempDisplayBelowTitle(error)
    }
}

async function fetchAndDisplayCatsLinks() {
    try {
        const catResponse = await Categories.fetchAllCats()
        const linkResponse = await Links.fetchAllLinks()
        displayCat(catResponse)
        displayLink(linkResponse)
    } catch (error) {
        tempDisplayBelowTitle(error)
    }
}



// ****** Category functions ******

function displayCat(catData) {
    const catListDiv = document.getElementById("cat-list")

    clearInnerHTML(catListDiv)
    const fragment = new DocumentFragment()

    Object.values(catData).forEach(cat => {
        const catName = cat.name

        const catHash = Utility.hash(catName)
        let categoryDiv = document.getElementById(catHash)

        // Check if a div for the category already exists
        if (!categoryDiv) {
            const br = document.createElement("br")
            fragment.append(br)

            categoryDiv = document.createElement("div")
            categoryDiv.id = catHash
            categoryDiv.textContent = `${catName} `

            const deleteSpan = createSpan(`elm${catHash}`, "minusSign", "Click to delete",
                "[-]", () => confirmDeleteCat(catHash))
            categoryDiv.append(deleteSpan)
            fragment.append(categoryDiv)
        }
    })
    catListDiv.append(fragment)
}

function displayEnterCat() {
    const enterCatDiv = document.getElementById("enter-cat")
    clearInnerHTML(enterCatDiv)

    const fragment = document.createDocumentFragment()

    const warningSpan = createSpan("above-enter-cat", "warning")
    fragment.append(warningSpan)

    fragment.append(document.createElement("br"))
    fragment.append(document.createElement("br"))

    const inputCat = document.createElement("input")
    inputCat.type = "text"
    inputCat.id = "cat"
    inputCat.name = "cat"
    inputCat.placeholder = "Enter category"
    fragment.append(inputCat)

    const inputLink = document.createElement("input")
    inputLink.type = "text"
    inputLink.id = "link"
    inputLink.name = "link"
    inputLink.placeholder = "Enter link"
    fragment.append(inputLink)

    const inputUrl = document.createElement("input")
    inputUrl.type = "text"
    inputUrl.id = "url"
    inputUrl.name = "url"
    inputUrl.placeholder = "Enter URL"
    inputUrl.value = "https://www."
    fragment.append(inputUrl)

    fragment.append(document.createElement("br"))
    fragment.append(document.createElement("br"))

    const submitButton = createButton("cat-submit", "Submit", subCreateLinkAndCat)
    fragment.append(submitButton)

    const cancelButton = createButton("cancel-cat", "Cancel", displayEnterCat)
    fragment.append(cancelButton)

    enterCatDiv.append(fragment)
}

function confirmDeleteCat(catId) {
    const catDivParent = document.getElementById(catId)

    // create a span to hold the delete confirmation text
    // it'll be the second span of the catDivParent
    let deleteCatTextSpan = createSpan(`confirm${catId}`)

    const thirdChild = catDivParent.childNodes[2]

    if (catDivParent.childElementCount < 2) { // cat with no links or delete text
        catDivParent.append(deleteCatTextSpan)
    } else if (thirdChild.nodeName === "LI") { // thirdChild is a link
        catDivParent.insertBefore(deleteCatTextSpan, thirdChild)
    } else { // thirdChild is delete text span
        catDivParent.removeChild(thirdChild)
        return
    }

    // Create a new div element for the delete confirmation text
    const deleteConfirmationDiv = document.createElement("div")
    deleteConfirmationDiv.className = "delete"
    deleteCatTextSpan.append(deleteConfirmationDiv)

    deleteConfirmationDiv.textContent = "Delete this entire category and all its links? "

    const confirmButton = createButton("delete-cat", "Confirm", () => removeCat(catId))
    deleteConfirmationDiv.append(confirmButton)

    const cancelButton = createButton("cancel-cat", "Cancel", () => cancel(catId))
    deleteConfirmationDiv.append(cancelButton)
}

async function removeCat(catId) {
    try {
        const result = await Categories.deleteCat(catId)
        if (result === true) {
            await fetchAndDisplayCatsLinks()
        } else {
            console.error("Categories.deleteCat() did not return true")
        }
    } catch (error) {
        console.error("Error in removeCat:", error)
    }
}



// ********* link functions **********

function displayLink(linkData) {
    linkData.forEach(link => {
        const linkObj = JSON.parse(link)
        const {category: cat, name: linkName, url, numVisits, dateTime: visitTime, id: linkId} = linkObj

        if (cat !== null) {
            const catHash = Utility.hash(cat)
            const catHashDiv = document.getElementById(catHash)

            const listItem = document.createElement("li")
            listItem.id = linkId
            listItem.className = "list"
            listItem.title = url

            const linkElement = document.createElement("a")
            linkElement.href = url
            linkElement.textContent = linkName
            linkElement.target = "_blank"
            linkElement.addEventListener("click", () => updateVisits(linkName, url))

            const spanElement = createSpan(`elm${linkId}`, "minusSign", "Click to delete",
                "[-]", () => confirmDeleteLink(linkId))

            listItem.append(linkElement, ` - ${numVisits} - ${visitTime} `, spanElement)
            catHashDiv.append(listItem)
        }
    })
}

async function updateVisits(linkName, url) {
    try {
        const result = await Links.incrementVisits(linkName, url)
        if (result === true) {
            await fetchAndDisplayCatsLinks()
        } else {
            console.error("updateVisits returned false")
        }
    } catch (error) {
        console.error("Error in updateVisits:", error)
    }
}

function confirmDeleteLink(id) {
    let parentElm = document.getElementById(id)

    if (parentElm.childElementCount < 3) {
        // display button to confirm delete
        let textSpan = createSpan(`confirm${id}`, "confirmText")
        parentElm.append(textSpan)

        const confirmDelete = document.getElementById(`confirm${id}`)
        clearInnerHTML(confirmDelete)

        const fragment = document.createDocumentFragment()

        fragment.append(document.createElement("br"))

        const deleteDiv = document.createElement("div")
        deleteDiv.className = "delete"
        deleteDiv.textContent = "Delete this link?"
        fragment.append(deleteDiv)

        const confirmButton = createButton("confirm-link", "Confirm", () => removeLink(id))
        deleteDiv.append(confirmButton)

        const cancelButton = createButton("cancel-link", "Cancel", () => cancel(id))
        deleteDiv.append(cancelButton)

        confirmDelete.append(fragment)
    } else if (parentElm.childElementCount === 3) {
        parentElm.removeChild(parentElm.children[2])
    }
}

async function removeLink(linkId) {
    try {
        const result = await Links.deleteLink(linkId)
        if (result === true) {
            await fetchAndDisplayCatsLinks()
        } else {
            console.error("removeLink did not return true")
        }
    } catch (error) {
        console.error("Error in removeLink:", error)
    }
}



// ********* helper functions  *********

function displayTempText(msg, elmId) {
    if (msg instanceof Error) {
        msg = msg.message
    }
    const elmToDisplayMsg = document.getElementById(elmId)
    elmToDisplayMsg.innerText = `*** Error:  ${msg}  ***`

    setTimeout(() => clearInnerHTML(elmToDisplayMsg), 5000)
}

function tempDisplayBelowTitle(msg) {
    // displayTempText(msg, "above-enter-cat")
    displayTempText(msg, "below-title")
}

function cancel(id) {
    let parentElm = document.getElementById(id)
    let child = document.getElementById(`confirm${id}`)
    parentElm.removeChild(child)
}

function createButton(id, text, funct) {
    const button = document.createElement("button")
    button.type = "button"
    button.id = id
    button.textContent = text
    button.addEventListener("click", funct)
    return button
}

function createSpan(id, className, title, text, eventListener) {
    const span = document.createElement("span")
    span.id = id
    className ? span.className = className : null
    title ? span.title = title : null
    span.textContent = text
    eventListener ? span.addEventListener("click", eventListener) : null
    return span
}

function clearInnerHTML(element) {
    while (element.firstChild) {
        element.removeChild(element.firstChild)
    }
}