// report.js

class AppReport extends HTMLElement {
    constructor() {
        super()
        this.attachShadow({ mode: 'open' })

        this._data = []
        this._loading = false
        this._error = null
    }

    set data(value) {
        this._data = value.apiResponse
        this._loading = false
        this._error = null
        this.render()
    }

    set loading(value) {
        this._loading = value
        this.render()
    }

    set error(message) {
        this._error = message
        this._loading = false
        this.render()
    }

    connectedCallback() {
        this.render()
    }

    render() {
        let content = ''

        if (this._loading) {
            content = `<div class="loading">Loading...</div>`
        } else if (this._error) {
            content = `<div class="error">Error: ${this._error}</div>`
        } else if (this._data && this._data.length) {
            content = this._data
                .map(
                    tenant => `
        <div class="tenant">
          <div class="tenant-title">${tenant.tenant}</div>
          <ul class="items">
            ${tenant.items
                        .map(
                            item => `
              <li class="item">
                <div class="description"><b>Message</b>: ${item.description}</div>
                <div class="threshold">
                  <b>ThresholdDelta</b>: ${item.thresholdDelta} 
                  ${item.thresholdDelta === -1 ? ' (underutilized)' : ''}
                </div>
              </li>
            `
                        )
                        .join('')}
          </ul>
        </div>
      `
                )
                .join('')
        } else {
            content = 'No data yet...'
        }

        this.shadowRoot.innerHTML = `
      <style>
        :host {
          display: block;
          font-family: system-ui, sans-serif;
        }

        .box {
          background: #f1f5f9;
          padding: 16px;
          border-radius: 12px;
          font-size: 14px;
          overflow: auto;
        }

        .tenant {
          margin-bottom: 16px;
        }

        .tenant-title {
          font-weight: 600;
          font-size: 16px;
          margin-bottom: 8px;
          color: #0f172a;
        }

        .items {
          list-style: none;
          padding-left: 16px;
        }

        .item {
          margin-bottom: 8px;
          padding: 8px;
          border-left: 3px solid #6366f1;
          background: #ffffff;
          border-radius: 6px;
        }

        .description {
          margin-bottom: 4px;
        }

        .threshold {
          font-weight: 500;
          color: #334155;
        }

        .loading {
          color: #6366f1;
          font-weight: 600;
        }

        .error {
          color: #dc2626;
          font-weight: 600;
        }
      </style>

      <div class="box">
        ${content}
      </div>
    `
    }
}

customElements.define('app-report', AppReport)