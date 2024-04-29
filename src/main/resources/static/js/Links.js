export async function fetchAllLinks() {
    const linkResponse = await fetch("/links")
    if (!linkResponse.ok) {
        throw new Error(`Failed to fetch links: ${linkResponse.status}`)
    }

    try {
        return await linkResponse.json()
    } catch (error) {
        console.error("Failed to parse response as JSON:", error)
        throw new Error("Something went wrong on the server.")
    }
}

export async function deleteLink(id) {
    try {
        const response = await fetch(`/delete-link?id=${id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        })

        if (!response.ok) {
            throw new Error("Failed to delete link")
        }

        const data = await response.json()

        // If the server returns { success: true } on successful deletion
        if (data.success) {
            return true
        }
    } catch (error) {
        console.error("Error deleting link:", error)
    }
    return false
}

export async function incrementVisits(linkName, url) {
    const requestData = {
        url: url
    }

    try {
        const response = await fetch("/update-link", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(requestData)
        })

        if (!response.ok) {
            throw new Error("Failed to update link")
        } else {
            return true
        }
    } catch (error) {
        console.error(error)
    }
    return false
}