export async function fetchAllCats() {
    const catResponse = await fetch("/cats")
    if (!catResponse.ok) {
        throw new Error(`Failed to fetch cats: ${catResponse.status}`)
    }

    try {
        return await catResponse.json()
    } catch (error) {
        console.error("Failed to parse categories response as JSON:", error)
        throw new Error("Something went wrong on the server.")
    }
}

export async function deleteCat(catHash) {    
    const catElem = document.getElementById(catHash)
    const catName = catElem.childNodes[0].textContent.trim()

    const requestData = {
        category: catName
    }

    try {        
        const response = await fetch("/delete-cat", {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        })        

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`)
        }

        const data = await response.json()

        // server returns { success: true } on successful deletion
        if (data.success) {
            return true
        }
    } catch (error) {
        console.error(error)
    }
    return false
}

